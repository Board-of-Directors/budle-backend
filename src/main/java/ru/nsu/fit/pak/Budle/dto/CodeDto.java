package ru.nsu.fit.pak.Budle.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CodeDto {
    private Long id;
    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;
}
