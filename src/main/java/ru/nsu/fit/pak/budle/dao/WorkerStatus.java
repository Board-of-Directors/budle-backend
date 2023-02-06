package ru.nsu.fit.pak.budle.dao;

public enum WorkerStatus {
    ABSENT(0), ON_WORK(1);

    public final Integer value;

    WorkerStatus(Integer value) {
        this.value = value;
    }
}
