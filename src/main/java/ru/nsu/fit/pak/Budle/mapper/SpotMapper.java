package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.Spot;
import ru.nsu.fit.pak.Budle.dto.SpotDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpotMapper {
    @Autowired
    private EstablishmentMapper establishmentMapper;
    public SpotDto modelToDto(Spot spot){
        SpotDto spotDto = new SpotDto();
        spotDto.setTags(spot.getTags());
        spotDto.setId(spot.getId());
        spotDto.setStatus(spot.getStatus());
        spotDto.setEstablishment(establishmentMapper.modelToDto(spot.getEstablishment()));
        return spotDto;
    }

    public List<SpotDto> ListModelToListDto(List<Spot> spots){
        List<SpotDto> spotDto = new ArrayList<>();
        for (Spot spot: spots){
            spotDto.add(modelToDto(spot));
        }
        return spotDto;
    }
}
