package com.sfl.scma.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfl.scma.security.jwt.JWTAuthenticationService;
import com.sfl.scma.security.jwt.domain.LoginPayload;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    public JWTLoginFilter(AuthenticationManager authManager) {
        this.setAuthenticationManager(authManager);
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException {
        LoginPayload loginPayload = new ObjectMapper().readValue(req.getInputStream(), LoginPayload.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(loginPayload.getUsername(),
                        loginPayload.getPassword())
                );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp,
                                            FilterChain chain, Authentication auth) {
        String token = JWTAuthenticationService.generateAuthHeader(auth);
        resp.addHeader("Authorization", token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
