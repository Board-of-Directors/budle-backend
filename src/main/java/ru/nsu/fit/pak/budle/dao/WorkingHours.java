package ru.nsu.fit.pak.budle.dao;

import lombok.Data;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table(name = "working_hours")
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    private Time startTime;

    private Time endTime;

    private Integer dayOfWeek;


}
