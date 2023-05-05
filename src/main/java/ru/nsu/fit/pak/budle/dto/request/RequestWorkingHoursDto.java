package ru.nsu.fit.pak.budle.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RequestWorkingHoursDto {
    @NotNull(message = "Дни недели не могут быть не заданы")
    @Size(min = 1, message = "Список дней не может быть пустым")
    private List<String> days;
    @NotNull(message = "Время начала работы не может быть не задано.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String startTime;
    @NotNull(message = "Время окончания работы не может быть не задано.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private String endTime;
}
