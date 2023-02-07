package ru.nsu.fit.pak.budle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Null
    private Long id;
    @NotNull
    @Size(min = 6, message = "Password cannot be less than 6 symbols")
    private String password;
    @NotNull
    @Size(min = 2, max = 255, message = "Name cannot be less than 2 symbols and more than 255")
    private String name;
    @NotNull
    @Size(min = 11, message = "Phone number cannot be less than 11 symbols")
    private String phoneNumber;
}
