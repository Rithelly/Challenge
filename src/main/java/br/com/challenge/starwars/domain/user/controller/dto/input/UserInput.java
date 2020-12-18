package br.com.challenge.starwars.domain.user.controller.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserInput extends UserWithoutPasswordInput {

    @ApiModelProperty(example = "teste@4341#")
    @NotBlank
    @Size(max = 100)
    private String senha;
}
