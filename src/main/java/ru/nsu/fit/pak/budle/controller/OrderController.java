package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void create(@RequestBody @Valid OrderDto dto) {
        orderService.createOrder(dto);

    }
}
