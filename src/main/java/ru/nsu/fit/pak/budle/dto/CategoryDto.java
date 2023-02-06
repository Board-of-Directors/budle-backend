package ru.nsu.fit.pak.budle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.fit.pak.budle.dao.Category;

@Data
@AllArgsConstructor
public class CategoryDto {
    Category category;
}
