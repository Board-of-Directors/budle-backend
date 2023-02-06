package ru.nsu.fit.pak.budle.dao;

public enum Category {
    restaurant("restaurant"),
    hotel("hotel"),
    bank("bank"),
    medicine("medicine"),
    government("government"),
    cars("cars"),
    entertainment("entertainment"),
    building("building");
    public final String value;

    Category(String value) {
        this.value = value;
    }

}