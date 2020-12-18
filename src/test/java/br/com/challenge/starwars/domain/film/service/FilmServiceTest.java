package br.com.challenge.starwars.domain.film.service;

import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.repository.FilmRepository;
import br.com.challenge.starwars.domain.film.repository.PersonRepository;
import br.com.challenge.starwars.exception.BusinessException;
import br.com.challenge.starwars.exception.EntityInUseException;
import br.com.challenge.starwars.exception.FilmNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ApplicationRunner.class,
        FilmRepository.class,
        FilmService.class,
        PersonRepository.class})
public class FilmServiceTest {
    private final String idFilm = "idFilm";

    @Autowired private FilmService filmService;

    @MockBean private FilmRepository filmRepository;
    @MockBean private PersonRepository personRepository;

    @Mock Film filmMock;

    @org.junit.Test
    @DisplayName("Deve falhar ao retornar um Filme por ID nulo.")
    public void FindByIdCalledWithNullArgumentsThrows() {
        FilmNotFoundException ex = null;
        Film film = null;

        try {
            film = filmService.findById(null);
        } catch (FilmNotFoundException e) {
            ex = e;
        }

        assertNull(film);
        assertNotNull(ex);
        assertEquals(FilmNotFoundException.class, ex.getClass());
        assertEquals(String.format("Não existe um cadastro de Filme de ID %s", null), ex.getMessage());
        verify(filmRepository, times(1)).findById(null);
    }

    @org.junit.Test
    @DisplayName("Deve retornar um Filme por ID")
    public void findByIdCalledWithValidArgumentsSuccess() {
        when(filmRepository.findById(idFilm)).thenReturn(Optional.of(filmMock));

        Optional<Film> filmFound = filmRepository.findById(idFilm);

        assertNotNull(filmMock);
        assertSame(filmMock, filmFound.get());
        verify(filmRepository, times(1)).findById(idFilm);
    }

    @org.junit.Test
    @DisplayName("Deve falhar ao salvar um Filme com objeto nulo.")
    public void saveCalledWithNullObjectThrows() {
        BusinessException ex = null;

        try {
            filmService.create(null);
        } catch (BusinessException e) {
            ex = e;
        }

        assertNotNull(ex);
        assertEquals(BusinessException.class, ex.getClass());
        assertEquals("Não foi informado objeto do filme para cadastro.", ex.getMessage());
        verify(filmRepository, never()).save( null);
    }

    @org.junit.Test
    @DisplayName("Deve falhar ao salvar um Filme com ID já existente")
    public void saveCalledWithExistingCodeThrows () {
        Optional<Film> filmOptional = Optional.of(filmMock);
        filmOptional.get().setIdFilm("1");
        when(filmMock.getIdFilm()).thenReturn("1");

        EntityInUseException ex = new EntityInUseException(String.format("Já existe um cadastro de Filme com ID %s", filmOptional.get().getIdFilm()));

        when(filmRepository.saveAndFlush(any(Film.class))).thenThrow(ex);

        try {
            filmService.create(filmMock);
        } catch (EntityInUseException e) {
            ex = e;
        }

        assertNotNull(ex);
        assertEquals(EntityInUseException.class, ex.getClass());
        assertEquals(String.format("Já existe um cadastro de Filme com ID %s", filmOptional.get().getIdFilm()), ex.getMessage());
    }

    @org.junit.Test
    @DisplayName("Deve salvar um Filme com sucesso")
    public void saveCalledWithValidObjectSuccess() {
        Film filmModel = createFilmWithId();

        when(filmRepository.saveAndFlush(any(Film.class))).thenReturn(filmMock);

        Film filmFound = filmService.create(filmModel);

        assertNotNull(filmMock);
        assertSame(filmMock, filmFound);
        verify(filmRepository, times(1)).saveAndFlush(any(Film.class));
    }

    private Film createFilmWithId() {
        return Film.builder()
                .idFilm(UUID.randomUUID().toString())
                .name("PERSONAGEM TESTE")
                .watched(br.com.challenge.starwars.domain.film.Watched.Y)
                .build();
    }
}
