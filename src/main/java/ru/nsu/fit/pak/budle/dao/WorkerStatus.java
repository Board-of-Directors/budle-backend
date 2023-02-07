package ru.nsu.fit.pak.budle.dao;

public enum WorkerStatus {
    absent(0), on_work(1);

    public final Integer value;

    WorkerStatus(Integer value) {
        this.value = value;
    }
}
