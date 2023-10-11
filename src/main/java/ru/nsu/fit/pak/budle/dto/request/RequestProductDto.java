package ru.nsu.fit.pak.budle.dto.request;

import lombok.Data;

@Data
public class RequestProductDto {
    private String name;
    private String price;
    private String weightG;
    private String description;
    private Long establishmentId;
    private Long categoryId;
    private boolean isOnSale;

}
