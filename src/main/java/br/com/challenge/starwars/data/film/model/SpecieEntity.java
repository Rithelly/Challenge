package br.com.challenge.starwars.data.film.model;

import br.com.challenge.starwars.domain.film.Specie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecieEntity implements Serializable {

    private String name;
    private String classification;
    private String designation;
    private String average_height;
    private String average_lifespan;
    private String eye_colors;
    private String hair_colors;
    private String skin_colors;
    private String language;
    private String homeworld;
    private Set<String> people;
    private Set<String> films;
    private String url;
    private String created;
    private String edited;
}
