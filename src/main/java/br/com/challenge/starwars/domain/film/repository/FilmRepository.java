package br.com.challenge.starwars.domain.film.repository;

import br.com.challenge.starwars.domain.film.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FilmRepository extends JpaRepository<Film, String>, JpaSpecificationExecutor<Film> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE film SET watched = :watched WHERE id_film = :idFilm", nativeQuery = true)
    Film updateWatched(String idFilm, int watched);
}
