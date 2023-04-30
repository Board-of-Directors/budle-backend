package ru.nsu.fit.pak.budle.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCodeDto {
    private Long id;
    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;
}
