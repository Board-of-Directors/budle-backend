package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;


@Component
public class EstablishmentMapper {

    public EstablishmentDto modelToDto(Establishment establishment) {
        UserMapper userMapper = new UserMapper();
        EstablishmentDto dto = new EstablishmentDto();
        dto.setId(establishment.getId());
        dto.setName(establishment.getName());
        dto.setDescription(establishment.getDescription());
        dto.setAddress(establishment.getAddress());
        dto.setOwner(userMapper.modelToDto(establishment.getOwner()));
        return dto;
    }
}
