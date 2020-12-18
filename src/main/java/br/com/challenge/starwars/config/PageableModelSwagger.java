package br.com.challenge.starwars.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel(value = "Pageable")
@Getter
@Setter
public class PageableModelSwagger {

    @ApiModelProperty(example = "0", value = "Número da página (Começa em 0)")
    private int size;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
    private int page;

    @ApiModelProperty(example = "nome, asc", value = "Nome da propriedade para ordenação")
    private List<String> sort;
}
