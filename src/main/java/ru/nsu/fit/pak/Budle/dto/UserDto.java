package ru.nsu.fit.pak.Budle.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class UserDto {
    @Null
    private Long id;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
