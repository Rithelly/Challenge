package br.com.challenge.starwars.exception.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ApiModel("Problema")
public class Problem {

    private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato "
            + "com o administrador do sistema.";

    @ApiModelProperty(example = "Dados Inválidos", position = 1)
    private String title;

    @ApiModelProperty(example = "400", position = 5)
    private Integer status;

    @ApiModelProperty(hidden = true)
    private String type;

    @ApiModelProperty(example = MSG_ERRO_GENERICA_USUARIO_FINAL, position = 10)
    private String detail;

    @JsonProperty("user_message")
    @ApiModelProperty(example = MSG_ERRO_GENERICA_USUARIO_FINAL, position = 15)
    private String userMessage;

    @ApiModelProperty(example = "2020-01-01T00:00:00000Z", value = "Data e Hora ISO", position = 20)
    private LocalDateTime timestamp;

    @JsonProperty("object_errors")
    @ApiModelProperty(example = "Um ou mais campos estão inválidos.", position = 25)
    private List<Object> objectErrors;

    Problem(String title, Integer status, String type, String detail, String userMessage, LocalDateTime timestamp, List<Object> objectErrors) {
        this.title = title;
        this.status = status;
        this.type = type;
        this.detail = detail;
        this.userMessage = userMessage;
        this.timestamp = timestamp;
        this.objectErrors = objectErrors;
    }

    public static ProblemBuilder builder() {
        return new ProblemBuilder();
    }

    @ApiModel("ObjetoProblema")
    @Getter
    public static class Object {
        @ApiModelProperty(example = "Nome")
        private String name;

        @ApiModelProperty(example = "Não pode ser nulo")
        private String message;

        Object(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public static ObjectBuilder builder() {
            return new ObjectBuilder();
        }

        public static class ObjectBuilder {
            private String name;
            private String message;

            ObjectBuilder() {
            }

            public ObjectBuilder name(String name) {
                this.name = name;
                return this;
            }

            public ObjectBuilder message(String message) {
                this.message = message;
                return this;
            }

            public Object build() {
                return new Object(name, message);
            }

            public String toString() {
                return "Problem.Object.ObjectBuilder(name=" + this.name + ", message=" + this.message + ")";
            }
        }
    }

    public static class ProblemBuilder {
        private String title;
        private Integer status;
        private String type;
        private String detail;
        private String userMessage;
        private LocalDateTime timestamp;
        private List<Object> objectErrors;

        ProblemBuilder() {
        }

        public ProblemBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ProblemBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ProblemBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ProblemBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ProblemBuilder userMessage(String userMessage) {
            this.userMessage = userMessage;
            return this;
        }

        public ProblemBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ProblemBuilder objectErrors(List<Object> objectErrors) {
            this.objectErrors = objectErrors;
            return this;
        }

        public Problem build() {
            return new Problem(title, status, type, detail, userMessage, timestamp, objectErrors);
        }

        public String toString() {
            return "Problem.ProblemBuilder(title=" + this.title + ", status=" + this.status + ", type=" + this.type + ", detail=" + this.detail + ", userMessage=" + this.userMessage + ", timestamp=" + this.timestamp + ", objectErrors=" + this.objectErrors + ")";
        }
    }

}
