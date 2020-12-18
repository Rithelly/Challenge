package br.com.challenge.starwars.domain.user.controller.dto.disassemble;

import br.com.challenge.starwars.domain.user.User;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserInput;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserWithoutPasswordInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public User toDomainObject(UserWithoutPasswordInput userWithoutPasswordInput) {
        return modelMapper.map(userWithoutPasswordInput, User.class);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        modelMapper.map(userInput, user);
    }

    public void copyToDomainObject(UserWithoutPasswordInput userWithoutPasswordInput, User user) {
        modelMapper.map(userWithoutPasswordInput, user);
    }
}
