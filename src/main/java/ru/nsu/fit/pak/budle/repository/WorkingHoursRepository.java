package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.WorkingHours;

/**
 * Repository, that connects working hours models with database.
 */
@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
}
