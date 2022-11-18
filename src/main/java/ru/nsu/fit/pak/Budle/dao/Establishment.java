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
@Table(name = "establishment_table")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 2, max = 30, message = "Name of your establishment cannot be less than 2 and larger than 30 symbols")
    private String name;
    @Column(unique = true)
    private String description;
    private String address;
    @OneToOne
    private User owner;


}
