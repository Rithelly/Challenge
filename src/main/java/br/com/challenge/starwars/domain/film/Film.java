package br.com.challenge.starwars.domain.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "film")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_film")
    private String idFilm;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "person_film",
            joinColumns = {
                    @JoinColumn(
                            name = "id_film",
                            referencedColumnName = "id_film"
                    )},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "id_person",
                            referencedColumnName = "id_person"
                    )})
    @JsonProperty(value = "people")
    private Set<Person> people = new HashSet<>();

    @Enumerated
    @NotNull
    private Watched watched;

    @PrePersist
    public void PrePersist() {
        idFilm = UUID.randomUUID().toString();
    }

    public boolean associatePerson(Person person) {
        return people.add(person);
    }
}
