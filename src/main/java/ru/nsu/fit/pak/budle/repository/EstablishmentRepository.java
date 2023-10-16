package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ShortEstablishmentDto;

import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT wh from Establishment e inner join WorkingHours wh " +
        "on e.id = wh.establishment.id where wh.dayOfWeek = :day order by wh.startTime")
    WorkingHours findWorkingHoursByDay(@Param("day") DayOfWeek day);


    List<ShortEstablishmentDto> findAllByOwner(User owner);

    Optional<Establishment> findByCategoryAndId(Category category, Long id);
}
