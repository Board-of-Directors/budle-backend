package ru.nsu.fit.pak.budle.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "establishments")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private Boolean hasMap;
    private Boolean hasCardPayment;
    private float rating;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private Category category;

    private String image;
    @OneToOne
    private User owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_establishments",
            joinColumns = @JoinColumn(name = "establishment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private List<Worker> workers;

    public Establishment(Category category, Boolean hasMap, Boolean hasCardPayment) {
        this.category = category;
        this.hasMap = hasMap;
        this.hasCardPayment = hasCardPayment;
    }
}
