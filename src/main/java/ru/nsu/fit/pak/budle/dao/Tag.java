package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Tag {
    wifi("WI-FI", "./assets/wifi.png"),
    power("Розетки", "./assets/power.png"),
    television("Телевизоры", "./assets/television.png"),
    quite("Тихое место", "./assets/quite.png"),
    kitchen("Кухня", "./assets/kitchen.png"),
    dance("Танцпол", "./assets/dance.png");

    public final String translate;

    public final String assets;

    Tag(String translate, String assets) {
        this.translate = translate;
        this.assets = assets;
    }

    public static Tag parseEnum(String name) {
        for (Tag tag : Tag.values()) {
            if (name.equals(tag.translate)) {
                return tag;
            }
        }
        throw new IncorrectDataException();
    }
}
