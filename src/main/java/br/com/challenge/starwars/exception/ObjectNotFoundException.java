package br.com.challenge.starwars.exception;

public abstract class ObjectNotFoundException extends BusinessException {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
