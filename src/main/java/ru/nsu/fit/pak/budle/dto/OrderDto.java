package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Integer guestCount;
    private LocalDateTime dateTime;
    private Long establishmentId;
    private Long userId;
}
