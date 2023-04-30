package ru.nsu.fit.pak.budle.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer guestCount;
    private Date date;
    private Time startTime;
    private Time endTime;

    @Transient
    private int duration = 240;

    private Integer status = 0;
    @OneToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Establishment establishment;

    public Order(User user, Establishment establishment, Integer status) {
        this.user = user;
        this.establishment = establishment;
        this.status = status;
    }

}
