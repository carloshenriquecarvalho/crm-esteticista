package br.com.pimentaestetica.crm.config;

import br.com.pimentaestetica.crm.model.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JWTTokenConfig {

    // Evite Hardcode. Pega do application.properties ou usa o fallback seguro
    @Value("${api.security.token.secret:51577e233289b6d81ae3177c28ce35b6051829255a698b811ad3ec810101659dc6153e5ea80c125ef9791c115913c88ee06f63b7082b775e929c725eceb258cb}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            Algorithm alg = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("pimenta-estetica-crm")
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getId().toString()) // Convertido para String plana
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS)) // Expiração padrão de 2h
                    .sign(alg);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(secretKey);
            return JWT.require(alg)
                    .withIssuer("pimenta-estetica-crm")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return ""; // Token inválido ou expirado retorna string vazia
        }
    }
}