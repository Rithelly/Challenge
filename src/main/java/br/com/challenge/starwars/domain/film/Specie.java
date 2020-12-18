package br.com.challenge.starwars.domain.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "specie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specie {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_specie")
    private String idSpecie;

    private String name;
    private String classification;
    private String language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime edited;

    @PrePersist
    public void PrePersist() {
        idSpecie = UUID.randomUUID().toString();
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void PreUpdate() {
        edited = LocalDateTime.now();
    }
}
