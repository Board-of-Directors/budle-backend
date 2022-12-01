package ru.nsu.fit.pak.Budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.Budle.dao.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
