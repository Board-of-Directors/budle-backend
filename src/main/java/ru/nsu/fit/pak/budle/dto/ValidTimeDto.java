package ru.nsu.fit.pak.budle.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class ValidTimeDto {
    private String dayName;
    private String monthName;
    private String dayNumber;
    private List<String> times;
}
