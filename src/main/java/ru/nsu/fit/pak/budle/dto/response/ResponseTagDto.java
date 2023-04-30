package ru.nsu.fit.pak.budle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dto.request.RequestTagDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTagDto extends RequestTagDto {
    private String image;

    public ResponseTagDto(String translate, String imageFromResource) {
        super(translate);
        this.image = imageFromResource;
    }
}
