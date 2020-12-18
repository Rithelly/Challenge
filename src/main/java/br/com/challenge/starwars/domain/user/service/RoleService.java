package br.com.challenge.starwars.domain.user.service;

import br.com.challenge.starwars.domain.user.Role;
import br.com.challenge.starwars.domain.user.repository.RoleRepository;
import br.com.challenge.starwars.exception.EntityInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@Service
public class RoleService {

    @Autowired private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findByNome(String nome) {
        return roleRepository.findByNome(nome);
    }

    public Role findById(String idFuncao) throws RoleNotFoundException {
        return roleRepository.findById(idFuncao).orElseThrow(() ->
                new RoleNotFoundException(idFuncao));
    }

    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public void delete(String idFuncao) throws RoleNotFoundException {
        try {
            roleRepository.deleteById(idFuncao);
            roleRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RoleNotFoundException(idFuncao);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException("A Função de ID %d não pode ser excluída, pois está em uso");
        }
    }
}
