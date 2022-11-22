package ru.nsu.fit.pak.Budle.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="spots")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String tags;
    String status;
    @OneToOne
    Establishment establishment;

}
