package ru.nsu.fit.pak.budle.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.baseUrl}")
    private String baseUrl;

    @Bean
    public OpenAPI customOpenApi() {
        Server server = new Server();
        server.setUrl(baseUrl);
        return new OpenAPI().servers(List.of(server));
    }
}
