package ru.nsu.fit.pak.Budle.service;

import java.io.IOException;

public interface CodeService {
    public boolean checkCode(String phoneNumber, String code);

    public void generateCode(String phoneNumber) throws IOException;
}
