package ru.nsu.fit.pak.budle.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ValidTimeDto {
    String dayName;
    String monthName;
    String dayNumber;
    List<String> times;
}
