package ru.nsu.fit.pak.budle.dao;

import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;

public enum Tag {
    wifi("WI-FI", "./assets/wifi.jpg"),
    power("Розетки", "./assets/zap.jpg"),
    television("Телевизоры", "./assets/tv.jpg"),
    quite("Тихое место", "./assets/headphones.jpg"),
    kitchen("Кухня", "./assets/eye.jpg"),
    dance("Танцпол", "./assets/music.jpg");

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
