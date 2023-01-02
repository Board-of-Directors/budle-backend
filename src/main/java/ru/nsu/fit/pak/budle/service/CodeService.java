package ru.nsu.fit.pak.budle.service;

import java.io.IOException;

public interface CodeService {
    boolean checkCode(String phoneNumber, String code);

    boolean generateCode(String phoneNumber) throws IOException;
}
