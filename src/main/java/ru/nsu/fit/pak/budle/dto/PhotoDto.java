package ru.nsu.fit.pak.budle.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhotoDto {
    private String image;

    public PhotoDto(String loadImage) {
        this.image = loadImage;
    }
}
