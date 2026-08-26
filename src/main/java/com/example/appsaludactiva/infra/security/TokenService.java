package com.example.appsaludactiva.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.appsaludactiva.domain.usuario.entity.Credenciales;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Value("${api.security.expiration-hours:2}")
    private Long expirationHours;

    public String generarToken (Credenciales credencial){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(apiSecret);

            return JWT.create()
                    .withIssuer("App Salud Activa") // Quien emite el token
                    .withSubject(credencial.getUsername()) // Usuario principal
                    .withClaim("id",credencial.getId()) // Datos adicionales (payload)
                    .withExpiresAt(generarFechaExpiracion()) // Fecha y hora límite
                    .sign(algoritmo); // Se genera la firma digital
            }catch (JWTCreationException exception){
                throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String getUsuario(String token){
        if (token == null || token.isBlank()){
            throw new RuntimeException("El token proporcionado es inválido o está vacío.");
        }
        try {
            Algorithm algoritmo = Algorithm.HMAC256(apiSecret);

            return JWT.require(algoritmo)
                    .withIssuer("App Salud Activa") // Valida que el emisor coincida
                    .build()
                    .verify(token) // Verifica firma y fecha de expiración
                    .getSubject(); // Retorna el username guardado en el token
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido o expirado.", exception);
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now()
                .plusHours(expirationHours)
                .toInstant(ZoneOffset.of("-06:00"));
    }
}
