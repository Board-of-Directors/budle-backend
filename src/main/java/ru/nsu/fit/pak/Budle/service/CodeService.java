package ru.nsu.fit.pak.Budle.service;

import java.io.IOException;

public interface CodeService {
    boolean checkCode(String phoneNumber, String code);

    boolean generateCode(String phoneNumber) throws IOException;
}
