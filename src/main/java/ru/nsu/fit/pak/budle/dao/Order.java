package ru.nsu.fit.pak.budle.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer guestCount;
    private LocalDateTime dateTime;
    @OneToOne
    private User user;

    @OneToOne
    private Spot spot;
}
