package br.com.challenge.starwars.data.film.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecieDetailEntity implements Serializable {

    private Integer count;
    private String next;
    private String previous;
    private Set<SpecieEntity> results;
}
