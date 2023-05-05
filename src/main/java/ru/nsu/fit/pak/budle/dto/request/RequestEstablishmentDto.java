package ru.nsu.fit.pak.budle.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import ru.nsu.fit.pak.budle.dto.PhotoDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@JsonTypeInfo(
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "category",
        use = JsonTypeInfo.Id.NAME,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestRestaurantDto.class, name = "Рестораны"),
        @JsonSubTypes.Type(value = RequestHotelDto.class, name = "Отели"),
        @JsonSubTypes.Type(value = RequestBarbershopDto.class, name = "Парикмахерские"),
        @JsonSubTypes.Type(value = RequestGameClubDto.class, name = "Игровые клубы")
})
@Data
public class RequestEstablishmentDto {
    @NotNull(message = "Имя не может быть не задано.")
    @Size(max = 200, message = "Название заведения не может превышать 200 символов.")
    private String name;
    @NotNull(message = "Описание не может быть не задано.")
    @Size(max = 1000, message = "Описание не может быть длиннее 1000 символов.")
    private String description;
    @NotNull(message = "Информация об адресе не может быть не задана.")
    @Size(max = 200, message = "Адрес не может быть длиннее 200 символов.")
    private String address;
    private Long owner;
    @NotNull(message = "Информация об оплате картой не может быть не задано.")
    private boolean hasCardPayment;
    @NotNull(message = "Информация о карте заведения не может быть не задана.")
    private boolean hasMap;
    @NotNull(message = "Категория не может быть не задана.")
    private String category;
    @NotNull(message = "Основное изображение не может быть не задано.")
    private String image;
    @Min(value = 1, message = "Рейтинг не может быть меньше 1.")
    @Max(value = 5, message = "Рейтинг не может быть больше 5.")
    @NotNull(message = "Рейтинг не может быть не задан.")
    private Float rating;
    @Max(value = 10000, message = "Средний чек не может быть больше 10000")
    @Min(value = 500, message = "Средний чек не может быть меньше 500")
    @NotNull(message = "Информация о среднем чеке не может быть не задана.")
    private Integer price;
    @NotNull(message = "Информация о рабочих часах заведения не может быть не задано.")
    @Size(min = 1, max = 7, message = "Дней работы не может быть меньше 1 и больше 7")
    @Valid
    private Set<RequestWorkingHoursDto> workingHours;
    @NotNull(message = "Информация о тэгах заведения не может быть не задана.")
    private Set<RequestTagDto> tags;
    @NotNull(message = "Информация о фотографиях заведения не может быть пустой.")
    private Set<PhotoDto> photosInput;
    private String map;

}
