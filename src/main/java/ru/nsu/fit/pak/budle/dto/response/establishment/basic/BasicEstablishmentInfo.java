package ru.nsu.fit.pak.budle.dto.response.establishment.basic;

public record BasicEstablishmentInfo(
    Long id,
    String name,
    String address,
    Float rating,
    String image,
    String category,
    Boolean hasMap,
    Boolean hasCardPayment,
    Integer starsCount,
    String cuisineCountry
) {
}
