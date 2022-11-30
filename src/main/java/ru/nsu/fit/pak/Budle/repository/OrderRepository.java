package ru.nsu.fit.pak.Budle.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.pak.Budle.dao.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
