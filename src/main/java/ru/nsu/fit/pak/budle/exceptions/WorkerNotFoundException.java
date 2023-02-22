package ru.nsu.fit.pak.budle.exceptions;

public class WorkerNotFoundException extends BaseException {

    public WorkerNotFoundException(Long id) {
        super("Работник с id " + id + " не существует", "WorkerNotFound");
    }
}
