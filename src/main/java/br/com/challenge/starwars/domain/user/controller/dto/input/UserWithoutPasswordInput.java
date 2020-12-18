package br.com.challenge.starwars.domain.user.controller.dto.input;

import br.com.challenge.starwars.domain.user.Situacao;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserWithoutPasswordInput {

    @ApiModelProperty(example = "lukeSkywalker@starwars.com.br")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty(example = "Luke Skywalker")
    @NotBlank
    @Size(max = 80)
    private String nome;

    @ApiModelProperty(example = "luke")
    @NotBlank
    @Size(max = 50)
    private String login;

    @ApiModelProperty(example = "ATIVO")
    private Situacao situacao = Situacao.ATIVO;
}
