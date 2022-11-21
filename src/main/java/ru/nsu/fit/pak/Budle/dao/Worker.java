package ru.nsu.fit.pak.Budle.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "worker_table")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean onWork;
    private String workerType;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_establishment",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "establishment_id"))
    private List<Establishment> establishments;
}
