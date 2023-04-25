package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;

/**
 * Repository, that connects spot models with database.
 */
@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    /**
     * Searching for list of all spots that connected with establishment.
     *
     * @param establishment - from what establishment we're searching spots.
     * @return list of all spots.
     */
    List<Spot> findByEstablishment(Establishment establishment);

    Spot findByEstablishmentAndLocalId(Establishment establishment, Long localId);
}
