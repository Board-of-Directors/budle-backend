package ru.nsu.fit.pak.budle.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;

import java.util.ArrayList;
import java.util.List;


@Component
public class EstablishmentMapper {

    @Autowired
    private UserMapper userMapper;

    public EstablishmentDto modelToDto(Establishment establishment) {
        EstablishmentDto dto = new EstablishmentDto();
        dto.setId(establishment.getId());
        dto.setName(establishment.getName());
        dto.setDescription(establishment.getDescription());
        dto.setAddress(establishment.getAddress());
        dto.setHasMap(establishment.getHasMap());
        dto.setHasCardPayment(establishment.getHasCardPayment());
        dto.setOwner(userMapper.modelToDto(establishment.getOwner()));
        dto.setCategory(establishment.getCategory());
        return dto;
    }

    public List<EstablishmentDto> modelListToDtoList(Iterable<Establishment> establishmentList) {
        List<EstablishmentDto> dtoList = new ArrayList<>();
        for (Establishment establishment : establishmentList) {
            dtoList.add(modelToDto(establishment));
        }
        return dtoList;
    }
}
