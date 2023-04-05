package ru.nsu.fit.pak.budle.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper configuration for our system.
 */
@Configuration
public class MapperConfiguration {
    /**
     * Bean that allows dependency injection for model mapper.
     *
     * @return instance of model mapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
