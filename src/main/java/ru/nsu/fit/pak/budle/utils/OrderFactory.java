package ru.nsu.fit.pak.budle.utils;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderWithSpot;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;

@Component
public class OrderFactory {
    public Class<? extends Order> getEntity(RequestOrderDto orderDto) {
        if (orderDto.getSpotId() == null) {
            return Order.class;
        } else {
            return OrderWithSpot.class;
        }
    }

}
