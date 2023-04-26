package ru.nsu.fit.pak.budle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * Swagger class, that configure auto creating API documentation.
 * You can search for swagger documentation at 80.64.174.33:8080/swagger-ui/#
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates instance of Docket class, that configure host URL, controller classes and
     * API paths.
     *
     * @return instance of Docket class.
     */
    @Bean
    public Docket create() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host("80.64.174.33:8080")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.nsu.fit.pak.budle.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
