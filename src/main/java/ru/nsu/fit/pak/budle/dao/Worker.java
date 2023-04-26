package ru.nsu.fit.pak.budle.dao;

import lombok.*;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private WorkerStatus status;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "worker_establishments",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "establishment_id"))
    private List<Establishment> establishments;
}
