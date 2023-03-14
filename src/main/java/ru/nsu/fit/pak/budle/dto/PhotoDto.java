package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

@Data
public class PhotoDto {
    private String image;

    public PhotoDto(String loadImage) {
        this.image = loadImage;
    }
}
