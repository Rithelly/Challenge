package br.com.challenge.starwars.config.secutiry;

import br.com.challenge.starwars.exception.exceptionhandler.GlobalExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {
        ModelMapper.class,
        GlobalExceptionHandler.class,
        SecurityTestConfig.class
})
public abstract class DefaultControllerTestSpringJUnitConfig {
}
