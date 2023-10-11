package ru.nsu.fit.pak.budle.dto.response.establishment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseProductDto {
    private String name;
    private String price;
    private String weightG;
    private String description;
    private boolean isOnSale;
}
