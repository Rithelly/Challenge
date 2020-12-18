package br.com.challenge.starwars.exception;

import java.util.function.Supplier;

public class FilmNotFoundException extends ObjectNotFoundException {

    public FilmNotFoundException(String idFilm) {
        super(String.format("Não existe um cadastro de Filme de ID %s",
                idFilm));
    }
}
