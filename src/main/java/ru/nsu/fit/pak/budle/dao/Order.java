package ru.nsu.fit.pak.budle.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.sql.Date;
import java.sql.Time;


@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status = OrderStatus.WAITING;
    @OneToOne
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Establishment establishment;
    @ManyToOne(fetch = FetchType.EAGER)
    private Spot spot;

    public Order(User user, OrderStatus status) {
        this.user = user;
        this.status = status;
    }

}
