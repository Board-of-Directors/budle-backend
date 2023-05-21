package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.exceptions.UserNotLoggedInException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        Object info = authentication.getPrincipal();
        if (info instanceof UserDetails userDetailsWithUsername) {
            log.info(userDetailsWithUsername.getUsername());
            return userDetailsWithUsername.getUsername();
        }
        log.warn("User details not instance of user details");
        return null;
    }

    @Override
    public User getLoggedInUser() {
        Object info = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (info instanceof User user) {
            log.info(user.getUsername());
            return user;
        }
        log.warn("Info is not instance of user details");
        throw new UserNotLoggedInException();
    }

    @Override
    public void autoLogin(String username, String password, HttpServletRequest request) {
        log.info("Auto login");
        log.info(username);
        log.info(password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            log.warn("User details was null");
            throw new UsernameNotFoundException("User with this username does not exist");
        }
        log.info(userDetails.toString());
        log.info("Auto login success");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        password,
                        userDetails.getAuthorities());


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
    }
}
