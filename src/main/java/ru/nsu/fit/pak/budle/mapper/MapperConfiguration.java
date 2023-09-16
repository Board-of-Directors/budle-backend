package ru.nsu.fit.pak.budle.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.OrderStatus;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.CuisineCountry;

/**
 * Mapper configuration for our system.
 */
@Configuration
public class MapperConfiguration {
    private final AbstractConverter<CuisineCountry, String> convertCuisine = new AbstractConverter<>() {
        @Override
        protected String convert(CuisineCountry source) {
            return source.getValue();
        }
    };
    private final AbstractConverter<String, CuisineCountry> convertToCuisine = new AbstractConverter<>() {

        @Override
        protected CuisineCountry convert(String source) {
            return CuisineCountry.getEnumByValue(source);
        }
    };
    private final AbstractConverter<Category, String> convertCategory = new AbstractConverter<>() {
        @Override
        protected String convert(Category source) {
            return source.value;
        }
    };
    private final AbstractConverter<String, Category> convertToCategory = new AbstractConverter<>() {

        @Override
        protected Category convert(String source) {
            return Category.getEnumByValue(source);
        }
    };
    private final AbstractConverter<OrderStatus, Integer> convertOrderStatus =
        new AbstractConverter<>() {
            @Override
            protected Integer convert(OrderStatus source) {
                return source.getStatus();
            }
        };

    /**
     * Bean that allows dependency injection for model mapper.
     *
     * @return instance of model mapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addConverter(convertCuisine);
        mapper.addConverter(convertCategory);
        mapper.addConverter(convertToCuisine);
        mapper.addConverter(convertToCategory);
        mapper.addConverter(convertOrderStatus);
        return mapper;
    }
}
