package ru.nsu.fit.pak.budle.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.nsu.fit.pak.budle.dao.User;

public interface SecurityService {

    User getLoggedInUser();

    void autoLogin(String username, String password, HttpServletRequest request);

}
