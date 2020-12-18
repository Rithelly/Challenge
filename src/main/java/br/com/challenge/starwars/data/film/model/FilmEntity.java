package br.com.challenge.starwars.data.film.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmEntity implements Serializable {

    private String title;
    private Integer episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private Date release_date;
    private Set<String> species;
    private Set<String> people;
    private String url;
    private String created;
    private String edited;
}
