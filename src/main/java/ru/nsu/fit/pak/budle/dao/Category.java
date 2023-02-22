package ru.nsu.fit.pak.budle.dao;

public enum Category {
    restaurant("рестораны"),
    hotel("отели"),
    bank("банки"),
    medicine("медицина"),
    government("государственные"),
    cars("машины"),
    entertainment("развлечения"),
    building("строительство");
    public final String value;

    Category(String value) {
        this.value = value;
    }

}