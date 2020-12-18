package br.com.challenge.starwars.domain.film.controller;

import br.com.challenge.starwars.domain.film.Person;
import br.com.challenge.starwars.domain.film.controller.swagger.PersonControllerSwagger;
import br.com.challenge.starwars.domain.film.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PersonController implements PersonControllerSwagger {

    @Autowired private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody @Valid Person person) {
        return personService.create(person);
    }

    @GetMapping("/filme/{idFilm}")
    public ResponseEntity<List<Person>> findCharacters(@PathVariable String idFilm) {
        List<Person> personList = personService.findByMovie(idFilm);
        return personService.findByMovie(idFilm).isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(personList);
    }

}
