package br.com.challenge.starwars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories(
        basePackages = {"br.com.challenge.starwars"}
)
public class ChallengeApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
        SpringApplication.run(ChallengeApplication.class, args);
    }

}
