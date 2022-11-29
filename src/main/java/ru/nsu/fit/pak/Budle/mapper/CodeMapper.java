package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.Code;
import ru.nsu.fit.pak.Budle.dto.CodeDto;

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
