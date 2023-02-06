package ru.nsu.fit.pak.budle.exceptions;

public class WorkerNotFoundException extends BaseException {
    public WorkerNotFoundException(String message) {
        super(message, "WorkerNotFound");
    }
}
