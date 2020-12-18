package br.com.challenge.starwars.data.film.controller;

import br.com.challenge.starwars.data.film.model.SpecieDetailEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("specieClientService")
public class SpecieClientController {


    @GetMapping
    public SpecieDetailEntity getSpecie() {
        RestTemplate restTemplate = new RestTemplate();
        SpecieDetailEntity specieEntity = restTemplate.getForObject("https://swapi.dev/api/species/", SpecieDetailEntity.class);
        return specieEntity;
    }
}
