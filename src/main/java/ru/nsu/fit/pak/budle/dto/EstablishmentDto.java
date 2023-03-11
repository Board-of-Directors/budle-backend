package ru.nsu.fit.pak.budle.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;


@JsonTypeInfo(
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "category",
        use = JsonTypeInfo.Id.NAME,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestaurantDto.class, name = "restaurant"),
        @JsonSubTypes.Type(value = HotelDto.class, name = "hotel"),
})
@Data
public class EstablishmentDto {
    @Null
    private Long id;
    @NotNull(message = "Имя не может быть пустым")
    @Size(max = 200)
    private String name;
    @NotNull(message = "Описание не может быть пустым")
    @Size(max = 1000)
    private String description;
    @NotNull(message = "Адрес не может быть пустым")
    @Size(max = 200)
    private String address;
    private UserDto owner;
    @NotNull(message = "Информация о оплате картой не может быть пустой")
    private boolean hasCardPayment;
    @NotNull(message = "Информация о карте заведения не может быть пустой")
    private boolean hasMap;
    @NotNull(message = "Категория не может быть пустой")
    private String category;
    @NotNull(message = "Картинка заведения не может быть пустой")
    private String image;
    @Min(value = 1, message = "Рейтинг не может быть меньше 1")
    @Max(value = 5, message = "Рейтинг не может быть больше 5")
    @NotNull(message = "Рейтинг не может быть пустым")
    private Float rating;
    @Max(value = 10000, message = "Средний чек не может быть больше 10000")
    @Min(value = 500, message = "Средний чек не может быть меньше 500")
    @NotNull(message = "Средний чек не может быть пустым")
    private Integer price;
    @NotNull(message = "Рабочие часы не могут быть пустыми.")
    @Size(min = 1, max = 7, message = "Дней работы не может быть меньше 1 и больше 7")
    private Set<WorkingHoursDto> workingHours;

}
