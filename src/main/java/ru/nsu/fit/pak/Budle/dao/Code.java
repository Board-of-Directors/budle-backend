package ru.nsu.fit.pak.Budle.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "user_codes")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;
}
