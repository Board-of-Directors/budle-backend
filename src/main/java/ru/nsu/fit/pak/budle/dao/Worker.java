package ru.nsu.fit.pak.budle.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_establishments",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "establishment_id"))
    private Set<Establishment> establishments;
}
