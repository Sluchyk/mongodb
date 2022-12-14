package com.example.mongodb.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import static com.example.mongodb.security.SecurityConstants.HEADER_STRING;
import static com.example.mongodb.security.SecurityConstants.SECRET;
import static com.example.mongodb.security.SecurityConstants.TOKEN_PREFIX;
import com.example.mongodb.service.impl.JwtUserDetailsService;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private JwtUserDetailsService jwtUserDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JwtUserDetailsService jwtUserDetailsService) {
        super(authManager);
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        var header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        var authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                Collection<? extends GrantedAuthority> authorities = jwtUserDetailsService.loadUserByUsername(user).getAuthorities();
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}

