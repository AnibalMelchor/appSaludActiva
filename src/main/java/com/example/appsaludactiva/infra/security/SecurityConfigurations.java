package com.example.appsaludactiva.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csfr -> csfr.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // 1. RUTAS PÚBLICAS (Sin token)
                    req.requestMatchers(HttpMethod.POST,"/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    req.requestMatchers("/v3/api-docs/**","/swagger-ui.html", "/swagger-ui/**").permitAll();

                    // 2. CRUD DE ALIMENTOS (Solo ADMIN crea, edita o elimina)
                    req.requestMatchers(HttpMethod.POST, "/alimentos/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/alimentos/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/alimentos/**").hasRole("ADMIN");

                    // 3. CRUD DE EJERCICIOS (Solo ADMIN crea, edita o elimina)
                    req.requestMatchers(HttpMethod.POST, "/ejercicios/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PUT, "/ejercicios/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.DELETE, "/ejercicios/**").hasRole("ADMIN");

                    // 4. CONSULTA DE CATÁLOGOS (GET accesible para cualquier usuario autenticado)
                    req.requestMatchers(HttpMethod.GET, "/alimentos/**").authenticated();
                    req.requestMatchers(HttpMethod.GET, "/ejercicios/**").authenticated();

                    req.anyRequest().authenticated();
                })
                // REGISTRO DE MANEJADORES DE ERRORES EN LA CAPA DE FILTROS:
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // Convierte faltante/inválido en 401
                        .accessDeniedHandler(customAccessDeniedHandler)             // Convierte falta de rol en 403
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
