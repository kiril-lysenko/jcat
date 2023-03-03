package com.self.education.catinfo.integration_tests.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class H2Config {

    @Bean
    public DataSource dataSource() {
        //@formatter:off
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("integration/db/db_setup.sql")
                .generateUniqueName(true).build();
        //@formatter:on
    }
}
