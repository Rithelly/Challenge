package br.com.challenge.starwars.domain.film.service;

import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.Person;
import br.com.challenge.starwars.domain.film.repository.FilmRepository;
import br.com.challenge.starwars.domain.film.repository.PersonRepository;
import br.com.challenge.starwars.exception.BusinessException;
import br.com.challenge.starwars.exception.EntityInUseException;
import br.com.challenge.starwars.exception.FilmNotFoundException;
import br.com.challenge.starwars.exception.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired private final FilmRepository filmRepository;
    @Autowired private final PersonRepository personRepository;

    public Film associatePerson(String idFilm, String idPerson) {
        Optional<Film> filmOptional = filmRepository.findById(idFilm);
        if (!filmOptional.isPresent()) {
            throw new FilmNotFoundException(idFilm);
        }

        Optional<Person> personOptional = personRepository.findById(idPerson);
        if (!personOptional.isPresent()) {
            throw new PersonNotFoundException(idPerson);
        }

        filmOptional.get().associatePerson(personOptional.get());
        return filmRepository.saveAndFlush(filmOptional.get());
    }

    @Transactional
    public Film create(Film film) {
        if (film == null) {
            throw new BusinessException("Não foi informado objeto do filme para cadastro.");
        }

        try {
            return filmRepository.saveAndFlush(film);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityInUseException(String.format("Já existe um cadastro de Filme com ID %s", film.getIdFilm()));
        }
    }

    @Transactional
    public Film update(String id, Film film) {
        if (findById(id) == null) {
            throw new FilmNotFoundException(id);
        }

        return filmRepository.saveAndFlush(film);
    }

    @Transactional
    public Film updateWatched(String id, boolean watched) {
        return filmRepository.updateWatched(id, watched ? 0 : 1);
    }

    @Transactional
    public void delete(String id) {
        try {
            filmRepository.deleteById(id);
            filmRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new EntityInUseException(String.format("Filme de ID %s não pode ser excluída, pois está em uso", id));
        } catch (EmptyResultDataAccessException exception) {
            throw new FilmNotFoundException(id);
        }

    }

    public Page<Film> findByCharacterName(Specification<Film> spec, Pageable pageable) {
        return filmRepository.findAll(spec, pageable);
    }

    public Film findById(String idFilme) {
        return filmRepository.findById(idFilme).orElseThrow(() ->new FilmNotFoundException(idFilme));
    }

}
