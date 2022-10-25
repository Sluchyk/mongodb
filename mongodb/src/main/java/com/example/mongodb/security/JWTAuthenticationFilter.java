package com.example.mongodb.security;

import com.auth0.jwt.JWT;
import com.example.mongodb.entity.dto.UserDto;
import static com.example.mongodb.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.mongodb.security.SecurityConstants.HEADER_STRING;
import static com.example.mongodb.security.SecurityConstants.SECRET;
import static com.example.mongodb.security.SecurityConstants.TOKEN_PREFIX;
import static com.example.mongodb.security.SecurityConstants.USER_ID_PARAM;
import com.example.mongodb.service.impl.JwtUserDetailsService;
import com.example.mongodb.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserServiceImpl userService;
    private  final JwtUserDetailsService jwtUserDetailsService;

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            var creds = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);
            Collection<? extends GrantedAuthority> authorities = jwtUserDetailsService.loadUserByUsername(creds.getUserName()).getAuthorities();
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserName(), creds.getPassword(),authorities));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        var user = (User) auth.getPrincipal();
        var userl = userService.getByName(user.getUsername());

        var token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim(USER_ID_PARAM, userl.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.setContentType("application/json");
    }
}

