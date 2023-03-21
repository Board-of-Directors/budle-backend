package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dto.SpotDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotMapper {
    private final ModelMapper modelMapper;

    public SpotDto modelToDto(Spot spot) {
        return modelMapper.map(spot, SpotDto.class);
    }

    public List<SpotDto> ListModelToListDto(List<Spot> spots) {
        return spots
                .stream()
                .map(spot -> modelMapper.map(spot, SpotDto.class))
                .toList();
    }
}
