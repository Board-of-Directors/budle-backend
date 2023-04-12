package ru.nsu.fit.pak.budle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserDto {
    @Null
    private Long id;
    @NotNull(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль не может быть короче 6 символов")
    private String password;
    @NotNull(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 255, message = "Имя пользователя не может быть короче 2 символов или длиннее 255")
    private String username;
    @NotNull(message = "Номер телефона не может быть пустым")
    @Size(min = 11, message = "Номер телефона не может быть короче 11 символов")
    private String phoneNumber;
}
