package ru.nsu.fit.pak.budle.dto.response.establishment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseMenuCategoryDto {
    private List<ResponseMenuCategoryDto> childCategories;
    private String name;
    private List<ResponseProductDto> products;
}
