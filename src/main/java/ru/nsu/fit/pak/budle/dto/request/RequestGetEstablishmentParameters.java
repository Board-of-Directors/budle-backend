package ru.nsu.fit.pak.budle.dto.request;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;

public record RequestGetEstablishmentParameters(@Nullable String name,
                                                @Nullable String category,
                                                @Nullable Boolean hasMap,
                                                @Nullable Boolean hasCardPayment,
                                                @Min(value = 0, message = "Страница не может быть меньше нулевой.") Integer offset,
                                                @Min(value = 1, message = "Лимит не может быть меньше единицы.") Integer limit,
                                                String sortValue) {

    public RequestGetEstablishmentParameters {
        if (name == null) name = "";
        if (offset == null) offset = 0;
        if (limit == null) limit = 100;
        if (sortValue == null) sortValue = "name";
    }
}
