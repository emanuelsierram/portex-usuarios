package com.portex.usuarios.infraestructura.seguridad.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenManager {

    private final Algorithm algorithm;

    public JwtTokenManager(@Value("${app.jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String crear(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("emanuelsierra17")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(algorithm);
    }

    public boolean esValido(String jwt) {
        try {
            JWT.require(algorithm).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String obtenerUsuario(String jwt) {
        return JWT.require(algorithm).build().verify(jwt).getSubject();
    }
}
