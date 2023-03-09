package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    Establishment getEstablishmentById(Long id);

    Boolean existsByAddressAndName(String address, String name);

}
