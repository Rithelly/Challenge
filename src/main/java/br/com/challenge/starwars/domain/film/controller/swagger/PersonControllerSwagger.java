package br.com.challenge.starwars.domain.film.controller.swagger;

import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.Person;
import br.com.challenge.starwars.domain.film.repository.spec.FilmSpec;
import br.com.challenge.starwars.exception.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Personagem")
public interface PersonControllerSwagger {

    @ApiOperation("Cadastra um Personagem")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Personagem cadastrado com sucesso")
    })
    Person create(@ApiParam(value = "Representação dos dados de um Personagem") Person person);


    @ApiOperation("Lista os Personagem por Filme")
    ResponseEntity<List<Person>> findCharacters(@ApiParam(value = "ID do Filme (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idFilm);
}

