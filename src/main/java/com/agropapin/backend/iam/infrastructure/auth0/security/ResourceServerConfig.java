package com.agropapin.backend.iam.infrastructure.auth0.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(configurer -> configurer.configurationSource(request  -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:5173"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH",  "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }));
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/authentication/**",
                                "ap/v1/roles/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/internal/webhooks/**",
                                "/api/v1/telemetry/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(auth0JwtToUserConverter()))
                );
        return http.build();
    }

    @Bean
    public Auth0JwtToUserConverter auth0JwtToUserConverter() {
        return new Auth0JwtToUserConverter();
    }
}
