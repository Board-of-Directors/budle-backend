package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.User;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    String findLoggedInUsername();

    User getLoggedInUser();

    void autoLogin(String username, String password, HttpServletRequest request);

}
