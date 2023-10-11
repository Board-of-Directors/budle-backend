package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.fit.pak.budle.dao.establishment.restaurant.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
