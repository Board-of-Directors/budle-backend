package ru.nsu.fit.pak.budle.dto.response.establishment.extended;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseWorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.basic.ResponseBasicEstablishmentInfo;

import java.util.Set;

@JsonSubTypes({
        @JsonSubTypes.Type(value = ResponseExtendedRestaurantInfo.class, name = "restaurant"),
        @JsonSubTypes.Type(value = ResponseExtendedHotelInfo.class, name = "hotel"),
        @JsonSubTypes.Type(value = ResponseExtendedBarbershopInfo.class, name = "barbershop"),
        @JsonSubTypes.Type(value = ResponseExtendedGameClubInfo.class, name = "game_club")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseExtendedEstablishmentInfo extends ResponseBasicEstablishmentInfo {
    private Set<ResponseTagDto> tags;
    private String description;
    private Set<PhotoDto> photos;
    private Set<ResponseWorkingHoursDto> workingHours;
    private String address;
}
