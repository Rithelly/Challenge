package br.com.challenge.starwars.domain.user.controller.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordInput {

    @NotBlank
    @ApiModelProperty(example = "senha@123")
    private String senhaAntiga;

    @NotBlank
    @ApiModelProperty(example = "novasenha@123")
    private String novaSenha;
}
