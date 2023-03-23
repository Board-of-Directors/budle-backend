package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final UserRepository userRepository;
    private final EstablishmentRepository establishmentRepository;

    private final SpotRepository spotRepository;

    public Order dtoToOrder(OrderDto dto) {
        Order order = new Order();
        order.setStatus(0);
        order.setDate(dto.getDate());
        order.setTime(dto.getTime());
        order.setGuestCount(dto.getGuestCount());
        order.setUser(userRepository.getReferenceById(dto.getUserId()));
        order.setEstablishment(establishmentRepository.getReferenceById(dto.getEstablishmentId()));
        order.setSpot(spotRepository.getReferenceById(dto.getSpotId()));
        return order;
    }
}
