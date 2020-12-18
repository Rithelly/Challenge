package br.com.challenge.starwars.exception;

public class UserNotFoundException extends ObjectNotFoundException {

    public UserNotFoundException(Integer idUsuario) {
        this(String.format("Não existe um cadastro de Usuário com ID %d", idUsuario));
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
