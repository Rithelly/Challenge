package br.com.challenge.starwars.exception;

/**
 * Classe mais abstrata para tratamento de erro
 * Utilizada para granularidade mais alta
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
