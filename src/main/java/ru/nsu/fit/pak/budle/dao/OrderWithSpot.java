package ru.nsu.fit.pak.budle.dao;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Getter
@Setter
public class OrderWithSpot extends Order {
    @ManyToOne(fetch = FetchType.EAGER)
    private Spot spot;
}
