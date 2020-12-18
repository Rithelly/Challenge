package br.com.challenge.starwars.domain.film.repository.spec;

import br.com.challenge.starwars.domain.film.Film;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public interface FilmSpec {

    @And({
            @Spec(path = "name", params = "name", spec = Like.class),
            @Spec(path = "watched", params = "watched", spec = Equal.class),
            @Spec(path = "people.name", params = "characterName", spec = Like.class),
    })
    interface PesquisaSpec extends Specification<Film> {}
}
