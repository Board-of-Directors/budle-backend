package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

/**
 * Repository, that connects establishment models with database.
 */
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    /**
     * Get establishment by provided id.
     *
     * @param id of establishment that we are searching for.
     * @return found establishment.
     */

    Establishment getEstablishmentById(Long id);

    /**
     * Checking existence of establishment by name and address.
     *
     * @param address provided address.
     * @param name    provided name.
     * @return true - if establishment exists, false - otherwise.
     */
    Boolean existsByAddressAndName(String address, String name);

}
