package br.com.challenge.starwars.domain.film.controller;

import br.com.challenge.starwars.config.secutiry.DefaultControllerTestSpringJUnitConfig;
import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.repository.spec.FilmSpec;
import br.com.challenge.starwars.domain.film.service.FilmService;
import br.com.challenge.starwars.exception.EntityInUseException;
import br.com.challenge.starwars.exception.exceptionhandler.ProblemType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FilmController.class)
@SpringJUnitConfig(classes = FilmController.class)
public class FilmControllerTest extends DefaultControllerTestSpringJUnitConfig {

    private static String RESOURCE_URI = "/filme";

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private FilmService filmService;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new FilmController(filmService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        JacksonTester.initFields(this, objectMapper);
    }

    @org.junit.Test
    @DisplayName("Deve retornar lista paginada de Filmes por nome do Personagem")
    public void findByCharacterNameCalledWithValidArgumentSuccess() throws Exception {
        final PageRequest pageable = PageRequest.of(0, 10);
        Film film = createFilmWithId();

        given(filmService.findByCharacterName(any(FilmSpec.PesquisaSpec.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(Collections.singletonList(film), pageable, 1));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", Matchers.hasSize(1)))
                .andExpect(jsonPath("content[0].idFilm").value(film.getIdFilm()))
                .andExpect(jsonPath("content[0].name").value(film.getName()))
                .andExpect(jsonPath("content[0].watched").value(film.getWatched().toString()))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("size").value(10))
                .andExpect(jsonPath("totalPages").value(1));

        verify(filmService, times(1)).findByCharacterName(any(FilmSpec.PesquisaSpec.class), any(Pageable.class));
    }

    @DisplayName("Deve cadastrar Filme com sucesso")
    @org.junit.Test
    public void postFilmCalledSuccess() throws Exception {
        final Film film = createFilmWithId();

        given(filmService.create(any(Film.class))).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idFilm").value(film.getIdFilm()))
                .andExpect(jsonPath("name").value(film.getName()))
                .andExpect(jsonPath("watched").value(film.getWatched().toString()));

        verify(filmService, times(1)).create(any(Film.class));
    }

    @DisplayName("Deve falhar ao cadastrar um Filme com campos inválidos")
    @org.junit.Test
    public void postFilmCalledFailed() throws Exception {
        final Film film = createFilmWithoutName();

        given(filmService.create(any(Film.class))).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("title").value(ProblemType.DADOS_INVALIDOS.getTitle()))
                .andExpect(jsonPath("detail").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("user_message").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("object_errors", hasSize(1)))
                .andExpect(jsonPath("object_errors[*].name", containsInAnyOrder("name")))
                .andExpect(jsonPath("object_errors[*].message",
                        containsInAnyOrder("must not be blank")));

        verify(filmService, never()).create(any(Film.class));
    }

    @DisplayName("Deve atualizar um Filme com sucesso")
    @org.junit.Test
    public void putFilmCalledSuccess() throws Exception {
        final String idFilme = "1";
        final Film film = createFilmWithoutId();

        given(filmService.update(anyString(), any(Film.class))).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(RESOURCE_URI.concat("/") + idFilme)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("idFilm").value(film.getIdFilm()))
                .andExpect(jsonPath("name").value(film.getName()))
                .andExpect(jsonPath("watched").value(film.getWatched().toString()));

        verify(filmService, times(1)).update(anyString(), any(Film.class));
    }

    @DisplayName("Deve atualizar um Filme para assistido com sucesso")
    @org.junit.Test
    public void putWatchedFilmCalledSuccess() throws Exception {
        final String idFilme = "1";
        final Film film = createFilmWithId();

        given(filmService.updateWatched(anyString(), anyBoolean())).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(RESOURCE_URI.concat("/assistido/") + idFilme)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("idFilm").value(film.getIdFilm()))
                .andExpect(jsonPath("name").value(film.getName()))
                .andExpect(jsonPath("watched").value(film.getWatched().toString()));

        verify(filmService, times(1)).updateWatched(anyString(), anyBoolean());
    }

    @DisplayName("Deve falhar ao atualizar um Filme com campos inválidos")
    @org.junit.Test
    public void putFilmCalledFailed() throws Exception {
        final String idFilme = "1";
        final Film film = createFilmWithoutName();

        given(filmService.update(anyString(), any(Film.class))).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(RESOURCE_URI.concat("/") + idFilme)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(film));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("title").value(ProblemType.DADOS_INVALIDOS.getTitle()))
                .andExpect(jsonPath("detail").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("user_message").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("object_errors", hasSize(1)))
                .andExpect(jsonPath("object_errors[*].name", containsInAnyOrder("name")))
                .andExpect(jsonPath("object_errors[*].message",
                        containsInAnyOrder("must not be blank")));

        verify(filmService, never()).update(anyString(), any(Film.class));
    }

    @DisplayName("Deve excluir Filme pelo ID")
    @org.junit.Test
    public void deleteFilmCalledSuccess() throws Exception {
        final String idFilme = "1";

        doNothing().when(filmService).delete(anyString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(RESOURCE_URI.concat("/") + idFilme)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(filmService, times(1)).delete(idFilme);
    }

    @DisplayName("Deve retornar status 409 ao excluir Filme em uso")
    @org.junit.Test
    public void deleteFilmInUseCalledFailed() throws Exception {
        final String idFilme = "1";
        final String errorMessage = String.format("Filme de ID %s não pode ser excluída, pois está em uso",
                idFilme);

        doThrow(new EntityInUseException(errorMessage)).when(filmService).delete(anyString());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(RESOURCE_URI.concat("/") + idFilme)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("title").value(ProblemType.ENTIDADE_EM_USO.getTitle()))
                .andExpect(jsonPath("detail").value(errorMessage))
                .andExpect(jsonPath("user_message").value(errorMessage));

        verify(filmService, times(1)).delete(idFilme);
    }

    @DisplayName("Deve associar um Personagem a um Filme pelo ID")
    @org.junit.Test
    public void associateFilmCalledSuccess() throws Exception {
        final String idFilm = "1";
        final String idPerson = "1";
        final Film film = createFilmWithId();

        given(filmService.associatePerson(anyString(), anyString())).willReturn(film);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(RESOURCE_URI.concat("/") + idFilm + "/associar/pessoa/" + idPerson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("idFilm").value(film.getIdFilm()))
                .andExpect(jsonPath("name").value(film.getName()))
                .andExpect(jsonPath("watched").value(film.getWatched().toString()));

        verify(filmService, times(1)).associatePerson(anyString(), anyString());
    }

    private Film createFilmWithId() {
        return Film.builder()
                .idFilm(UUID.randomUUID().toString())
                .name("PERSONAGEM TESTE")
                .watched(br.com.challenge.starwars.domain.film.Watched.Y)
                .build();
    }

    private Film createFilmWithoutId() {
        return Film.builder()
                .name("PERSONAGEM TESTE")
                .watched(br.com.challenge.starwars.domain.film.Watched.Y)
                .build();
    }

    private Film createFilmWithoutName() {
        return Film.builder()
                .idFilm(UUID.randomUUID().toString())
                .name(null)
                .watched(br.com.challenge.starwars.domain.film.Watched.Y)
                .build();
    }
}
