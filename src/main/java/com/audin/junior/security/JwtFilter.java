package com.audin.junior.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.audin.junior.entity.Jwt;
import com.audin.junior.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Allow preflight requests
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        String username = null;
        UserDetails userDetails = null;
        boolean isTokenExpire = true;

        String path = request.getServletPath();

        // Ignore public routes
        System.out.println(path);
        if(path.startsWith("/auth/")){
            filterChain.doFilter(request, response);
            return;
        }
        

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            isTokenExpire = jwtService.isTokenExpire(token);
            username = this.jwtService.getUserName(token);
        }
        System.out.println("TOKEN TROUVEEE :: : " + token);
        
        final Jwt jwtDB = this.jwtService.findByToken(token);
        if (!isTokenExpire &&
                username != null &&
                jwtDB.getUser().getEmail().equals(username) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            userDetails = this.userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response);
    }

}
