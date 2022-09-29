package ru.nsu.fit.pak.Budle.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "JC_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 2, max = 20, message = "Your name cannot be less than 2 and larger than 20 symbols")
    private String firstName;
    @Size(min = 2, max = 20, message = "Your name cannot be less than 2 and larger than 20 symbols")
    private String lastName;
    @Column(unique = true)
    private String phoneNumber;
    @Size(min = 6)
    private String pass;
    private int userType;


}
