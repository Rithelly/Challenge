package br.com.challenge.starwars.domain.user.repository;

import br.com.challenge.starwars.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByNome(String nome);
}
