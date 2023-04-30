package ru.nsu.fit.pak.budle.dto.response.establishment.extended;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.fit.pak.budle.dto.PhotoListDto;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
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
    private PhotoListDto photos;
    private Set<WorkingHoursDto> workingHours;
    private String address;
}
