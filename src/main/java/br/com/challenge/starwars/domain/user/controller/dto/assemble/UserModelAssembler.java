package br.com.challenge.starwars.domain.user.controller.dto.assemble;

import br.com.challenge.starwars.domain.user.User;
import br.com.challenge.starwars.domain.user.controller.dto.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserModel toModel(User user) {
        return modelMapper.map(user, UserModel.class);
    }

    public List<UserModel> toCollectionModel(List<User> users) {
        return users.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
 }
