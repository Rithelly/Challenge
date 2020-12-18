package br.com.challenge.starwars.domain.film.service;

import br.com.challenge.starwars.domain.film.Person;
import br.com.challenge.starwars.domain.film.repository.PersonRepository;
import br.com.challenge.starwars.domain.film.repository.SpecieRepository;
import br.com.challenge.starwars.exception.BusinessException;
import br.com.challenge.starwars.exception.SpecieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Autowired private final PersonRepository personRepository;
    @Autowired private final SpecieRepository specieRepository;

    @Transactional
    public Person create(Person person) {
//        if (!specieRepository.findById(person.getSpecie().getIdSpecie()).isPresent()) {
//            throw new SpecieNotFoundException(person.getSpecie().getIdSpecie());
//        }

        if (StringUtils.hasText(person.getIdPerson())) {
            if (personRepository.existsById(person.getIdPerson())) {
                throw new BusinessException(
                        String.format("Já existe um cadastro de Pessoa com o ID %s", person.getIdPerson()));
            }
        }

        return personRepository.saveAndFlush(person);
    }

    public List<Person> findByMovie(String idFilm) {
        if (!StringUtils.hasText(idFilm)) {
            throw new IllegalArgumentException("Não foi informado ID do filme para realizar a busca.");
        }

        return personRepository.findAllByIdFilm(idFilm);
    }

}
