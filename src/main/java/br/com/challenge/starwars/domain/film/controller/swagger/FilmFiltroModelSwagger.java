package br.com.challenge.starwars.domain.film.controller.swagger;

import br.com.challenge.starwars.domain.film.Watched;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Filme")
@Getter
@Setter
public class FilmFiltroModelSwagger {

    @ApiModelProperty(example = "A New Hope", value = "Nome do Filme (EQUAL)")
    private String name;

    @ApiModelProperty(example = "Y,N", value = "Situação de visualização do Filme (EQUAL)")
    private Watched watched;

    @ApiModelProperty(example = "Luke Skywalker", value = "Nome do Personagem (EQUAL)")
    private String characterName;
}
