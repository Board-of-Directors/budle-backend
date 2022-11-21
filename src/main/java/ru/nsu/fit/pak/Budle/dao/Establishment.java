package ru.nsu.fit.pak.Budle.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "establishment_table")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 2, max = 30, message = "Name of your establishment cannot be less than 2 and larger than 30 symbols")
    private String name;
    @Column(unique = true)
    private String description;
    private String address;
    private Boolean hasMap;
    private Boolean hasCardPayment;
    @OneToOne
    private User owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_establishment",
            joinColumns = @JoinColumn(name = "establishment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private List<Worker> workers;


}
