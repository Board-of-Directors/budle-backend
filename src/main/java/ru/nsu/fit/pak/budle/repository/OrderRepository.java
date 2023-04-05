package ru.nsu.fit.pak.budle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;

import java.util.List;

/**
 * Repository, that connects order models with database.
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    /**
     * Found all orders, that was creating by provided user.
     *
     * @param user which orders we're searching.
     * @return list of all orders.
     */
    List<Order> findAllByUser(User user);

    /**
     * Found all orders, that was creating with provided establishment.
     *
     * @param establishment which orders we're searching.
     * @return list of all orders.
     */

    List<Order> findAllByEstablishment(Establishment establishment);
}
