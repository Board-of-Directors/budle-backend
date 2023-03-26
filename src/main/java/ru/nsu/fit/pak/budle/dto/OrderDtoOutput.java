package ru.nsu.fit.pak.budle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class OrderDtoOutput {
    private Long id;
    private Integer guestCount;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time time;
    private Integer status;
    private EstablishmentDto establishment;
    private Long userId;
}
