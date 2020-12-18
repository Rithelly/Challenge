package br.com.challenge.starwars.data.film.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleEntity implements Serializable {

    private String name;
    private String birth_year;
    private String eye_color;
    private String gender;
    private String hair_color;
    private String height;
    private String mass;
    private String skin_color;
    private String homeworld;
    private Set<String> films;
    private Set<String> species;
    private String url;
    private String created;
    private String edited;
}
