package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Tag {
    wifi("WI-FI", "/wifi.svg"),
    power("Розетки", "/zap.svg"),
    television("Телевизоры", "/tv.svg"),
    quite("Тихое место", "/headphones.svg"),
    kitchen("Кухня", "/eye.svg"),
    dance("Танцпол", "/music.svg");

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
