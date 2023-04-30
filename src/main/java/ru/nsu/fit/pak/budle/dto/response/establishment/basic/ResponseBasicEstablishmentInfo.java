package ru.nsu.fit.pak.budle.dto.response.establishment.basic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseBasicRestaurantInfo.class, name = "restaurant"),
        @JsonSubTypes.Type(value = ResponseBasicHotelInfo.class, name = "hotel"),
        @JsonSubTypes.Type(value = ResponseBasicBarbershopInfo.class, name = "barbershop"),
        @JsonSubTypes.Type(value = ResponseBasicGameClubInfo.class, name = "game_club")
})
public class ResponseBasicEstablishmentInfo extends ResponseShortEstablishmentInfo {
    private Float rating;
    private String address;
    private String image;

}
