package ru.nsu.fit.pak.budle.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;

@Entity
@Data
@Table(name = "photo")
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    @Column(name = "filename")
    private String filepath;

    public Photo(String filepath) {
        this.filepath = filepath;
    }

}
