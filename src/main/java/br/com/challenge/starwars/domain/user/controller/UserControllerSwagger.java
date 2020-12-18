package br.com.challenge.starwars.domain.user.controller;

import br.com.challenge.starwars.domain.user.controller.dto.input.UserInput;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserPasswordInput;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserWithoutPasswordInput;
import br.com.challenge.starwars.domain.user.controller.dto.model.UserModel;
import br.com.challenge.starwars.domain.user.repository.spec.UserSpec;
import br.com.challenge.starwars.exception.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Usuários")
public interface UserControllerSwagger {

    @ApiOperation("Lista os Usuários")
    Page<UserModel> getAll(@ApiParam(name = "email", value = "Filtro por e-mail", example = "rba@solucoes.com.br")
                                   UserSpec.EmailSpec emailSpec,
                           @ApiParam(value = "Parâmetros de Paginação")
                                   Pageable pageable);

    @ApiOperation("Busca um Usuário pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UserModel getById(@ApiParam(value = "ID do Usuário", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idUsuario);

    @ApiOperation("Busca um Usuário pelo Login")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Login inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Login não encontrado", response = Problem.class)
    })
    UserModel getByLogin(@ApiParam(value = "Login do Usuário", example = "carlos") String login);

    @ApiOperation("Cadastrar um Usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado com sucesso")
    })
    UserModel create(@ApiParam(value = "Representação de dados de um usuário") UserInput userInput);

    @ApiOperation("Atualiza um Usuário")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UserModel update(@ApiParam(value = "ID do Usuário", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idUsuario,
                     @ApiParam(value = "Representação de novos dados de um usuário sem senha") UserWithoutPasswordInput userWithoutPasswordInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha atualizada com sucesso"),
            @ApiResponse(code = 400, message = "ID do Usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void updatePassword(@ApiParam(value = "ID do Usuário", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idUsuario,
                        @ApiParam(value = "Representação da nova senha do usuário") UserPasswordInput userPasswordInput);

    @ApiOperation("Exclui um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário excluído com sucesso"),
            @ApiResponse(code = 400, message = "ID do Usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void delete(@ApiParam(value = "ID do Usuário", example = "f027659e-c12c-11ea-b3de-0242ac130004") String idUsuario);
}
