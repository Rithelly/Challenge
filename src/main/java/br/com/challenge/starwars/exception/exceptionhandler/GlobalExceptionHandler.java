package br.com.challenge.starwars.exception.exceptionhandler;

import br.com.challenge.starwars.exception.BusinessException;
import br.com.challenge.starwars.exception.EntityInUseException;
import br.com.challenge.starwars.exception.ObjectNotFoundException;
import br.com.challenge.starwars.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    public static final String MSG_ERRO_GENERICA_PARA_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente e se o problema persistir, " +
            "entre em contato com o administrador do sistema";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;

        Problem problem = createProblemBuilder(status, MSG_ERRO_GENERICA_PARA_USUARIO_FINAL, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return validateFields(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        return validateFields(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    public ResponseEntity<Object> validateFields(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String details = "Um ou mais campos estão inválidos. Corrija-os e tente novamente";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError ->  {
                    String message =  messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .message(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, details, problemType)
                .userMessage(details)
                .objectErrors(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String details = String.format("O recurso '%s' não existe", ex.getRequestURL());
        Problem problem = createProblemBuilder(status, details, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String details = String.format(
                "O parâmetro de URL '%s' possui o valor '%s', que é de um tipo inválido. Informe um valor do tipo %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName().toUpperCase());

        Problem problem = createProblemBuilder(status, details, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição contém problemas. Verifique erro de sintaxe";

        Problem problem = createProblemBuilder(status, detail, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format(
                "A propriedade '%s' não existe. Verifique-a e tente novamente", path
        );

        Problem problem = createProblemBuilder(status, detail, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s' que pertence a um tipo inválido. " +
                        "O valor compatível com a propriedade deve ser do tipo %s",
                path, ex.getValue(), ex.getTargetType().getSimpleName().toUpperCase());

        Problem problem = createProblemBuilder(status, detail, problemType)
                .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<?> handleHttpRequestObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        String details = ex.getMessage();

        Problem problem = createProblemBuilder(httpStatus, details, problemType)
                .userMessage(details)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
    }


    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(httpStatus, detail, problemType)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinesException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String details = ex.getMessage();

        ProblemType problemType = ProblemType.ERRO_DE_NEGOCIO;

        Problem problem = createProblemBuilder(status, details, problemType)
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleMissingPathVariable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_PARA_USUARIO_FINAL)
                    .build();
        }

        log.error(ex.getMessage());

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus HttpStatus,
                                                        String detail,
                                                        ProblemType problemType) {
        return Problem.builder()
                .status(HttpStatus.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
