package br.com.challenge.starwars.domain.user.service;

import br.com.challenge.starwars.domain.user.Role;
import br.com.challenge.starwars.domain.user.User;
import br.com.challenge.starwars.domain.user.controller.dto.input.UserPasswordInput;
import br.com.challenge.starwars.domain.user.repository.UserRepository;
import br.com.challenge.starwars.exception.BusinessException;
import br.com.challenge.starwars.exception.EntityInUseException;
import br.com.challenge.starwars.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RoleService roleService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(Specification<User> spec) {
        return userRepository.findAll(spec);
    }

    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    public User findById(String idUsuario) {
        return userRepository.findById(idUsuario).orElseThrow(() ->
                new UserNotFoundException(idUsuario));
    }

    public boolean existsById(String idUsuario) {
        return userRepository.existsById(idUsuario);
    }

    public User findByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login).orElseThrow(()
                -> new UserNotFoundException(
                String.format("Não existe um Usuário com login %s cadastrado no sistema", login)));
    }

    @Transactional
    public User save(User user) {
        Optional<User> actualUserEmail = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (actualUserEmail.isPresent() && !user.equals(actualUserEmail.get())) {
            throw new BusinessException("Não foi possível cadastrar usuário. Email já cadastrado no sistema");
        }

        Optional<User> actualUserLogin = userRepository.findByLoginIgnoreCase(user.getLogin());
        if (actualUserLogin.isPresent() && !user.equals(actualUserLogin.get())) {
            throw new BusinessException("Não foi possível cadastrar usuário. Login já cadastrado no sistema");
        }

        Role role = roleService.findByNome("ADM");
        Role newRole = new Role();
        if (role == null) {
            newRole.setNome("ADM");
            newRole.setIdFuncao(UUID.randomUUID().toString());
            newRole = roleService.save(newRole);
        } else {
            newRole = role;
        }

        user.associateRole(newRole);
        
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void updatePassword(User user, UserPasswordInput userPasswordInput) {
        if (!passwordEncoder.matches(userPasswordInput.getSenhaAntiga(), user.getSenha())) {
            throw new BusinessException("Senha informada não coincide com a senha do usuário");
        }

        user.setSenha(passwordEncoder.encode(userPasswordInput.getNovaSenha()));
    }

    @Transactional
    public void delete(String idUsuario) {
        try {
            userRepository.deleteById(idUsuario);
            userRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(idUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(
                    "Não é possível excluir o usuário de id %d, pois está em uso", idUsuario));
        }
    }
}
