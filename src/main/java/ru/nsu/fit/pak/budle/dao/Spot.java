package ru.nsu.fit.pak.budle.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "spot")
    private Set<Order> orders;

    public Spot(Long localId, Establishment establishment) {
        this.localId = localId;
        this.establishment = establishment;
    }

}
