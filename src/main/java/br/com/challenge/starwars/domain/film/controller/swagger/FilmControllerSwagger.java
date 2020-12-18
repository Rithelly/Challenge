package br.com.challenge.starwars.domain.film.controller.swagger;

import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.repository.spec.FilmSpec;
import br.com.challenge.starwars.exception.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Filmes")
public interface FilmControllerSwagger {

    @ApiOperation("Lista os Filmes Com Filtro (name, watched, personagem)")
    Page<Film> findByCharacterName(FilmSpec.PesquisaSpec pesquisaSpec,
                                   @ApiParam(value = "Parâmetros de Paginação") Pageable pageable);

    @ApiOperation("Cadastra um Filme")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Filme cadastrado com sucesso")
    })
    Film create(@ApiParam(value = "Representação dos dados de um Filme") Film film);

    @ApiOperation("Atualiza um Filme")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Filme não encontrado", response = Problem.class)
    })
    Film update(@ApiParam(value = "ID do Filme (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idFilm,
                @ApiParam(value = "Representação dos novos dados de um Filme") Film film);

    @ApiOperation("Atualiza um Filme Para Assistido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Filme atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Filme não encontrado", response = Problem.class)
    })
    Film updateWatched(@ApiParam(value = "ID do Filme (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idFilm,
                @ApiParam(value = "Representação de assistido ou não", example = "true") boolean watched);

    @ApiOperation("Associa uma Pessoa a um Filme")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Filme associado com sucesso"),
            @ApiResponse(code = 400, message = "ID inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Filme não encontrado", response = Problem.class)
    })
    Film associatePerson(@ApiParam(value = "ID do Filme (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idFilm,
                         @ApiParam(value = "ID da Pessoa (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idPerson);

    @ApiOperation("Exclui um Filme")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Filme excluído com sucesso"),
            @ApiResponse(code = 404, message = "Filme não encontrado", response = Problem.class)
    })
    void delete(@ApiParam(value = "ID do Filme (UUID)", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idCliente);
}

