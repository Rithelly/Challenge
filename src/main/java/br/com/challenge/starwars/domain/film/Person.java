package br.com.challenge.starwars.domain.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_person")
    private String idPerson;

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birth_year")
    @NotNull
    private Integer birthYear;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime edited;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_specie", referencedColumnName = "id_specie")
//    @JsonProperty(value = "specie")
//    @NotNull
//    private Specie specie;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "people", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Film> films = new HashSet<>();

    @PrePersist
    public void PrePersist() {
        idPerson = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void PreUpdate() {
        edited = LocalDateTime.now();
    }
}
