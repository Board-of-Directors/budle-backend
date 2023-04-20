package ru.nsu.fit.pak.budle.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


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
    private Time time;

    private Integer status = 0;
    @OneToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Establishment establishment;

}
