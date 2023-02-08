package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    void findAllByUser(User user);
}
