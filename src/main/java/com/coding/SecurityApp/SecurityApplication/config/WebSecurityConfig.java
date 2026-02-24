package com.coding.SecurityApp.SecurityApplication.config;


import com.coding.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.coding.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import com.coding.SecurityApp.SecurityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coding.SecurityApp.SecurityApplication.entities.enums.Permission.*;
import static com.coding.SecurityApp.SecurityApplication.entities.enums.Role.ADMIN;
import static com.coding.SecurityApp.SecurityApplication.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/posts/**").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/posts/**")
//                            .hasAnyRole(ADMIN.name(), CREATOR.name())
//                        .requestMatchers(HttpMethod.POST, "/posts/**")
//                        .hasAnyAuthority(POST_CREATE.name())
//                        .requestMatchers(HttpMethod.PUT, "/posts/**")
//                        .hasAnyAuthority(POST_UPDATE.name())
//                        .requestMatchers(HttpMethod.DELETE, "/posts/**")
//                        .hasAnyAuthority(POST_DELETE.name())
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler));
        return httpSecurity.build();
     }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
