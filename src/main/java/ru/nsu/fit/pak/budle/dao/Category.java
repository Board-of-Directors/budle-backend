package ru.nsu.fit.pak.budle.dao;

public enum Category {
    restaurant("Рестораны"),
    hotel("Отели"),
    bank("Банки"),
    medicine("Медицинские"),
    government("Государственные"),
    cars("Автомобильные"),
    entertainment("Развлекательный"),
    building("Строительные");
    public final String value;

    Category(String value) {
        this.value = value;
    }

}