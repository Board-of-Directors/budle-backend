package ru.nsu.fit.pak.budle.dao.establishment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.Photo;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.WorkingHours;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
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
    private String map;
    @OneToOne(fetch = FetchType.LAZY)
    private User owner;
    @ManyToMany
    @JoinTable(name = "worker_establishments",
            joinColumns = @JoinColumn(name = "establishment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private Set<Worker> workers;
    @OneToMany(mappedBy = "establishment")
    private Set<Order> orders;
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "establishment",
            cascade = CascadeType.ALL)
    private Set<WorkingHours> workingHours;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "establishment", cascade = CascadeType.ALL)
    private Set<Photo> photos;
    @ElementCollection(targetClass = Tag.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "establishment_tags", joinColumns = @JoinColumn(name = "establishment_id"))
    @Column(name = "tag_name")
    private Set<Tag> tags;

    public Establishment(Category category, Boolean hasMap, Boolean hasCardPayment, String name) {
        this.category = category;
        this.hasMap = hasMap;
        this.hasCardPayment = hasCardPayment;
        this.name = name;
    }
}
