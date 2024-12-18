package com.historialplus.historialplus.auth;

import com.historialplus.historialplus.auth.filter.JwtAuthenticationFilter;
import com.historialplus.historialplus.auth.filter.JwtValidationFilter;
import com.historialplus.historialplus.service.AuthService.IAuthService;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringSecurityConfig {

    private static final String JWT_SECRET = System.getenv("JWT_SECRET_RECORD_PLUS") != null
            ? System.getenv("JWT_SECRET_RECORD_PLUS")
            : "default_secret_key_for_testing_purposes";

    private static final String FRONTEND_URL = System.getenv("FRONTEND_URL") != null
            ? System.getenv("FRONTEND_URL")
            : "http://localhost:3000";

    @Bean
    SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, SecretKey jwtSecretKey, IAuthService authService) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtSecretKey, authService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager, jwtSecretKey);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("MANAGEMENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/createManagementUser").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/createDoctorUser").hasRole("MANAGEMENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/compress-image").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pdf/compress").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/hospitals").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/hospitals").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/records").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/records/{documentNumber}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/records").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/files").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/files/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/files").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/record-details").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/record-details/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/record-details/record/{recordId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/record-details").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/record-details/{id}/state").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/hospitals/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(FRONTEND_URL));
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}