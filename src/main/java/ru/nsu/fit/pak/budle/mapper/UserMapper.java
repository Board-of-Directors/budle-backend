package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;


    public User dtoToModel(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public List<UserDto> modelListToDtoList(List<User> userList) {
        return userList
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
