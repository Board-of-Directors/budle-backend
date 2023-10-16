package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.MenuCategory;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findAllByEstablishment(Establishment establishment);
}
