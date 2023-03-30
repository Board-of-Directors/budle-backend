package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Tag {
    wifi("WI-FI", "/wifi.svg", "Около Wi-Fi"),
    power("Розетки", "/zap.svg", "Около розетки"),
    television("Телевизоры", "/tv.svg", "Около телевизора"),
    quite("Тихое место", "/headphones.svg", "Тихое место"),
    kitchen("Кухня", "/eye.svg", "Около кухни"),
    dance("Танцпол", "/music.svg", "Около танцпола");

    public final String translate;

    public final String assets;

    public final String translateForSpot;

    Tag(String translate, String assets, String translateForSpot) {
        this.translate = translate;
        this.assets = assets;
        this.translateForSpot = translateForSpot;
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
