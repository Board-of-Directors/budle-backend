package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.Spot;

import java.util.List;

public interface SpotServiceInterface {
    List<Spot> getSpotsByEstablishment(Establishment establishment);
}
