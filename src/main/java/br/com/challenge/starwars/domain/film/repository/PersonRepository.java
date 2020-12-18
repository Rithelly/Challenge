package br.com.challenge.starwars.domain.film.repository;

import br.com.challenge.starwars.domain.film.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String>, JpaSpecificationExecutor<Person> {

    @Query("SELECT p FROM Person p LEFT JOIN p.films f WHERE f.idFilm = :idFilm")
    List<Person> findAllByIdFilm(String idFilm);
}
