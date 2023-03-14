package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PhotoListDto {
    private int count;
    private Set<PhotoDto> set;
}
