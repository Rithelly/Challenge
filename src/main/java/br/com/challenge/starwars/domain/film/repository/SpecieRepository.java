package br.com.challenge.starwars.domain.film.repository;

import br.com.challenge.starwars.domain.film.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, String>, JpaSpecificationExecutor<Specie> {

}
