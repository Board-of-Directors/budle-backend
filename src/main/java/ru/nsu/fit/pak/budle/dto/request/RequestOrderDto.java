package ru.nsu.fit.pak.budle.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class RequestOrderDto {
    @Min(value = 1, message = "Заказ не может быть на отрицательное количество людей.")
    private Integer guestCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    private Long establishmentId;
    private Long userId;
    private Long spotId;
}
