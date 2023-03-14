package ru.nsu.fit.pak.budle.dao;

import lombok.Data;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;

@Entity
@Data
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    @Column(name = "file_path")
    private String filepath;

}
