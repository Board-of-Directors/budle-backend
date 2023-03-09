package ru.nsu.fit.pak.budle.dao.establishment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.Worker;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "establishments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private Boolean hasMap;
    private Boolean hasCardPayment;
    private Float rating;
    private Integer price;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", insertable = false, updatable = false)
    private Category category;

    private String image;
    @OneToOne
    private User owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker_establishments",
            joinColumns = @JoinColumn(name = "establishment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private List<Worker> workers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<Order> orders;

    public Establishment(Category category, Boolean hasMap, Boolean hasCardPayment) {
        this.category = category;
        this.hasMap = hasMap;
        this.hasCardPayment = hasCardPayment;
    }
}
