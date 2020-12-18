package br.com.challenge.starwars.exception;

public class PersonNotFoundException extends ObjectNotFoundException {

    public PersonNotFoundException(String idPerson) {
        super(String.format("Não existe um cadastro de Personagem de ID %s",
                idPerson));
    }
}
