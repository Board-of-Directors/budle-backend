package ru.nsu.fit.pak.budle.dto;

import lombok.Data;
import ru.nsu.fit.pak.budle.dao.Category;

import javax.validation.constraints.*;

@Data
public class EstablishmentDto {
    @Null
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(max = 200)
    private String name;
    @NotNull(message = "Description cannot be null")
    @Size(max = 1000)
    private String description;
    @NotNull(message = "Address cannot be null")
    @Size(max = 200)
    private String address;
    private UserDto owner;
    @NotNull(message = "Card payment flag cannot be null")
    private boolean hasCardPayment;
    @NotNull(message = "Map flag cannot be null")
    private boolean hasMap;
    @NotNull(message = "Category cannot be null")
    private Category category;
    @NotNull(message = "Image cannot be null")
    private String image;
    @Min(value = 1, message = "Rating cannot be less than 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    @NotNull(message = "Rating cannot be null")
    private Float rating;
    @Max(value = 10000, message = "Price cannot be more than 10000")
    @Min(value = 500, message = "Price cannot be less than 500")
    @NotNull(message = "Price cannot be null")
    private Integer price;
}
