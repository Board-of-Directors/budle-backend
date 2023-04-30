package ru.nsu.fit.pak.budle.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;

import java.sql.Time;
import java.util.Date;

@Data
public class ResponseOrderDto {
    private Long id;
    private Integer guestCount;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time endTime;
    private Integer status;
    private RequestEstablishmentDto establishment;
    private Long userId;
}
