package ru.nsu.fit.pak.budle.dao;

import lombok.Data;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "working_hours")
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer dayOfWeek;


}
