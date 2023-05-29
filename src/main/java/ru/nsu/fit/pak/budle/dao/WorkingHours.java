package ru.nsu.fit.pak.budle.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "working_hours")
public class WorkingHours {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wh_seq")
    @SequenceGenerator(name = "wh_seq", sequenceName = "working_hours_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;


}
