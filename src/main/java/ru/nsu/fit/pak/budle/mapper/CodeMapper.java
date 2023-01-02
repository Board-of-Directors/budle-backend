package ru.nsu.fit.pak.budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Code;
import ru.nsu.fit.pak.budle.dto.CodeDto;

@Component
public class CodeMapper {
    public CodeDto modelToDto(Code code){
        CodeDto dto = new CodeDto();
        dto.setCode(code.getCode());
        dto.setId(code.getId());
        dto.setCreatedAt(code.getCreatedAt());
        dto.setPhoneNumber(code.getPhoneNumber());
        return dto;
    }

}
