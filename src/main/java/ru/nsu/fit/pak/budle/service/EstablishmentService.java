package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.EstablishmentDto;

import java.util.List;

public interface EstablishmentService {
    List<EstablishmentDto> getEstablishments();
    EstablishmentDto getEstablishmentById(Long id);

    List<EstablishmentDto> getEstablishmentsByCategory(String category);
}
