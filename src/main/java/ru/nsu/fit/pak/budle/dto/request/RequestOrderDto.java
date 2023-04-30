package ru.nsu.fit.pak.budle.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestOrderDto {
    private Integer guestCount;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Time time;
    private Long establishmentId;
    private Long userId;
    private Long spotId;
}
