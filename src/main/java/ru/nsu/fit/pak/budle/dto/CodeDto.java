package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodeDto {
    private Long id;
    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;
}
