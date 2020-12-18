package br.com.challenge.starwars.domain.film.controller;

import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.controller.swagger.FilmControllerSwagger;
import br.com.challenge.starwars.domain.film.repository.spec.FilmSpec;
import br.com.challenge.starwars.domain.film.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/filme")
@RequiredArgsConstructor
public class FilmController implements FilmControllerSwagger {

    @Autowired private final FilmService filmService;

    @GetMapping
    public Page<Film> findByCharacterName(FilmSpec.PesquisaSpec pesquisaSpec, Pageable pageable) {
        return filmService.findByCharacterName(pesquisaSpec, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody @Valid Film film) {
        return filmService.create(film);
    }

    @PutMapping("/{idFilm}")
    public Film update(@PathVariable String idFilm, @RequestBody @Valid Film film) {
        return filmService.update(idFilm, film);
    }

    @PutMapping("/assistido/{idFilm}")
    public Film updateWatched(@PathVariable String idFilm, boolean watched) {
        return filmService.updateWatched(idFilm, watched);
    }

    @PutMapping("/{idFilm}/associar/pessoa/{idPerson}")
    public Film associatePerson(@PathVariable String idFilm, @PathVariable String idPerson) {
        return filmService.associatePerson(idFilm, idPerson);
    }

    @DeleteMapping("/{idFilm}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String idFilm) {
        filmService.delete(idFilm);
    }
}
