package ru.nsu.fit.pak.budle.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "spots")
@NoArgsConstructor
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long localId;
    @OneToOne
    private Establishment establishment;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "spot")
    private Set<Order> orders;

    public Spot(Long localId, Establishment establishment) {
        this.localId = localId;
        this.establishment = establishment;
    }

}
