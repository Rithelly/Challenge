package br.com.challenge.starwars.domain.user.repository;

import br.com.challenge.starwars.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.login = :login AND u.situacao = 'ATIVO'")
    User findByLogin(String login);

//    @Query("SELECT u FROM User u WHERE u.login = :login AND u.situacao = 'ATIVO'")
//    Optional<User> findByLogin(String login);

    @Query("SELECT u from User u WHERE u.email = :email OR u.login = :login")
    Optional<User> findByEmailOrLogin(String email, String login);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByLoginIgnoreCase(String email);
}
