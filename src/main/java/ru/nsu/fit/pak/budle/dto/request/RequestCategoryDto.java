package ru.nsu.fit.pak.budle.dto.request;

import lombok.Data;

@Data
public class RequestCategoryDto {
    private String name;
    private Long parentCategoryId;
    private Long establishmentId;
}
