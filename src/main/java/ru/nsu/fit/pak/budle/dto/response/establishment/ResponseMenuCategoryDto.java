package ru.nsu.fit.pak.budle.dto.response.establishment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.nsu.fit.pak.budle.dto.response.ShortResponseMenuCategoryDto;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseMenuCategoryDto extends ShortResponseMenuCategoryDto {
    private List<ResponseMenuCategoryDto> childCategories;
    private List<ResponseProductDto> products;
    private String name;
    private Long id;
}
