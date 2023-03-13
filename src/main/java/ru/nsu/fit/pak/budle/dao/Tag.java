package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Tag {
    wifi("WI-FI", "/wifi.jpg"),
    power("Розетки", "/zap.jpg"),
    television("Телевизоры", "/tv.jpg"),
    quite("Тихое место", "/headphones.jpg"),
    kitchen("Кухня", "/eye.jpg"),
    dance("Танцпол", "/music.jpg");

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
