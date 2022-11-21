package ru.nsu.fit.pak.Budle.dao;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Boolean onWork;
    private String workerType;
    @OneToOne
    private User user;
}
