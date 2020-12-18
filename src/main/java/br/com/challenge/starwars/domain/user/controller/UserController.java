package br.com.challenge.starwars.domain.user.controller;

import br.com.challenge.starwars.domain.user.User;
import br.com.challenge.starwars.domain.user.controller.dto.assemble.UserModelAssembler;
import br.com.challenge.starwars.domain.user.controller.dto.disassemble.UserInputDisassembler;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserInput;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserPasswordInput;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserWithoutPasswordInput;
import br.com.challenge.starwars.domain.user.controller.dto.model.UserModel;
import br.com.challenge.starwars.domain.user.repository.spec.UserSpec;
import br.com.challenge.starwars.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/usuarios")
@RequiredArgsConstructor
public class UserController implements UserControllerSwagger {

    @Autowired private final UserService userService;
    @Autowired private final UserModelAssembler userModelAssembler;
    @Autowired private final UserInputDisassembler userInputDisassembler;

    @Override
    @GetMapping
    public Page<UserModel> getAll(UserSpec.EmailSpec emailSpec,
                                  Pageable pageable) {
        Page<User> users = userService.findAll(emailSpec, pageable);
        List<UserModel> usersModel = userModelAssembler.toCollectionModel(users.getContent());

        return new PageImpl<>(usersModel, pageable, users.getTotalElements());
    }

    @Override
    @GetMapping("/{idUsuario}")
    public UserModel getById(@PathVariable String idUsuario) {
        return userModelAssembler.toModel(userService.findById(idUsuario));
    }

    @Override
    @GetMapping("/login/{login}")
    public UserModel getByLogin(@PathVariable String login) {
        return userModelAssembler.toModel(userService.findByLogin(login));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel create(@RequestBody @Valid UserInput userInput) {
        User user = userInputDisassembler.toDomainObject(userInput);
        return userModelAssembler.toModel(userService.save(user));
    }

    @Override
    @PutMapping("/{idUsuario}")
    public UserModel update(@PathVariable String idUsuario,
                            @RequestBody @Valid UserWithoutPasswordInput userWithoutPasswordInput) {
        User user = userService.findById(idUsuario);
        userInputDisassembler.copyToDomainObject(userWithoutPasswordInput, user);

        return userModelAssembler.toModel(userService.save(user));
    }

    @Override
    @PutMapping("/{idUsuario}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable String idUsuario,
                               @RequestBody @Valid UserPasswordInput userPasswordInput) {
        User user = userService.findById(idUsuario);

        userService.updatePassword(user, userPasswordInput);
    }

    @Override
    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String idUsuario) {
        userService.delete(idUsuario);
    }
}

