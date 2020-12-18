package br.com.challenge.starwars.config;


import br.com.challenge.starwars.domain.film.controller.swagger.FilmFiltroModelSwagger;
import br.com.challenge.starwars.domain.film.repository.spec.FilmSpec;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Autowired private TypeResolver typeResolver;

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("Challenge AS2")
                .description("API REST com informações sobre Filmes e Personagens da saga STAR WARS.")
                .version("1.0.0")
                .contact(new Contact("Rithelly Silva", "https://www.rsilva.com.br/", "rithelly56@gmail.com"))
                .build();
    }

    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfoV1())
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("br.com.challenge.starwars"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())

                .ignoredParameterTypes(InputStream.class)

                .directModelSubstitute(Pageable.class, PageableModelSwagger.class)

                .directModelSubstitute(FilmSpec.PesquisaSpec.class, FilmFiltroModelSwagger.class)

                .tags(
                        new Tag("Filmes",  "Gerencia todos Filmes da Saga."),
                        new Tag("Personagem",  "Gerencia todos Personagens da Saga."),
                        new Tag("Usuários",  "Gerencia todos usuários da aplicação.")
                )

                .additionalModels(typeResolver.resolve(Problem.class));
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

