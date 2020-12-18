package br.com.challenge.starwars.exception;

public class SpecieNotFoundException extends ObjectNotFoundException {

    public SpecieNotFoundException(String idSpecie) {
        super(String.format("Não existe um cadastro de Especie de ID %s",
                idSpecie));
    }
}
