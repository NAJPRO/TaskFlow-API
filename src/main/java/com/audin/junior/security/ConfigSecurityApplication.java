package com.audin.junior.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.audin.junior.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ConfigSecurityApplication {

    //private final AuthenticationProvider authenticationProvider;
    private final ConfigEncodingPassword encodingPassword;
    private final JwtFilter jwtFilter;

    //private final UserService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return
            httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                    authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS ,"/**").permitAll()
                        .requestMatchers(HttpMethod.POST ,"/auth/**").permitAll()

                        .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(
                    session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) 
                .build();
    }

 

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userDetailsService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(this.encodingPassword.passwordEncoder());
        return authProvider;
    }
}

