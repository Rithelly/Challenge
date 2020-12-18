package br.com.challenge.starwars.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;
}
