package br.com.challenge.starwars.domain.user.controller.dto.model;

import br.com.challenge.starwars.domain.user.Situacao;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    @ApiModelProperty(example = "f027659e-c12c-11ea-b3de-0242ac130004")
    private String idUsuario;

    @ApiModelProperty(example = "lukeSkywalker@starwars.com.br")
    private String email;

    @ApiModelProperty(example = "Luke Skywalker")
    private String nome;

    @ApiModelProperty(example = "luke")
    private String login;

    @ApiModelProperty(example = "ATIVO")
    private Situacao situacao;
}
