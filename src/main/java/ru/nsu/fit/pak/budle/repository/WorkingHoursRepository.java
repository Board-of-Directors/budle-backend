package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.budle.dao.WorkingHours;

/**
 * Repository, that connects working hours models with database.
 */
public interface WorkingHoursRepository extends CrudRepository<WorkingHours, Long> {
}
