package br.com.challenge.starwars.domain.film.controller;

import br.com.challenge.starwars.config.secutiry.DefaultControllerTestSpringJUnitConfig;
import br.com.challenge.starwars.domain.film.Film;
import br.com.challenge.starwars.domain.film.Person;
import br.com.challenge.starwars.domain.film.service.PersonService;
import br.com.challenge.starwars.exception.exceptionhandler.ProblemType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
@SpringJUnitConfig(classes = PersonController.class)
public class PersonControllerTest extends DefaultControllerTestSpringJUnitConfig {
    private static String RESOURCE_URI = "/pessoa";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private PersonService personService;

    @DisplayName("Deve cadastrar um Personagem com sucesso")
    @org.junit.Test
    public void postFilmCalledSuccess() throws Exception {
        final Person person = createPersonWithoutId();

        given(personService.create(any(Person.class))).willReturn(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("idPerson").value(person.getIdPerson()))
                .andExpect(jsonPath("name").value(person.getName()))
                .andExpect(jsonPath("birthYear").value(person.getBirthYear()))
                .andExpect(jsonPath("created", is(person.getCreated().format(formatter))))
                .andExpect(jsonPath("edited").value(person.getEdited()));

        verify(personService, times(1)).create(any(Person.class));
    }

    @DisplayName("Deve falhar ao cadastrar um Personagem com campos inválidos")
    @org.junit.Test
    public void postPersonCalledFailed() throws Exception {
        final Person person = createPersonWithoutName();

        given(personService.create(any(Person.class))).willReturn(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RESOURCE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("title").value(ProblemType.DADOS_INVALIDOS.getTitle()))
                .andExpect(jsonPath("detail").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("user_message").value("Um ou mais campos estão inválidos. Corrija-os e tente novamente"))
                .andExpect(jsonPath("object_errors", hasSize(1)))
                .andExpect(jsonPath("object_errors[*].name", containsInAnyOrder("name")))
                .andExpect(jsonPath("object_errors[*].message",
                        containsInAnyOrder("must not be blank")));

        verify(personService, never()).create(any(Person.class));
    }

    public Person createPersonWithoutId() {
        return Person.builder()
                .birthYear(1997)
                .name("LUKE SKYWALKER")
                .created(LocalDateTime.now())
                .build();
    }

    public Person createPersonWithoutName() {
        return Person.builder()
                .idPerson(UUID.randomUUID().toString())
                .birthYear(1997)
                .created(LocalDateTime.now())
                .edited(LocalDateTime.now())
                .build();
    }
}
