package br.com.challenge.starwars.config;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile({"dev", "prod"})
public class FlywayConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public Flyway flyway(DataSource dataSource) {
        logger.info("Migrating test schema ");

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration/default",  "db/test")
                .load();

        flyway.migrate();

        return flyway;
    }
}
