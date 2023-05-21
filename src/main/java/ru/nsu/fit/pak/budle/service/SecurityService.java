package ru.nsu.fit.pak.budle.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password, HttpServletRequest request);

}
