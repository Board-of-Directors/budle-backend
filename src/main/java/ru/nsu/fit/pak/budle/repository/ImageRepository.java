package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.budle.dao.Photo;

public interface ImageRepository extends CrudRepository<Photo, Long> {
}
