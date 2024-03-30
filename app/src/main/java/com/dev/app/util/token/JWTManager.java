package com.dev.app.util.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JWTManager {

    private final static int TOKEN_LIFESPAN = 3600 * 1000; //ONE HOUR



    private Claims extractAllClaims(String bearer, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(bearer)
                .getBody();
    }

    private Optional<String> extractUsername(JsonWebToken jwt, Key secretKey) {
        Claims claims = extractAllClaims(jwt.bearer(), secretKey);
        return Optional.of(claims.getSubject());
    }

    private Optional<Date> extractExpiration(JsonWebToken jwt, Key secretKey) {
        Claims claims = extractAllClaims(jwt.bearer(), secretKey);
        return Optional.of(claims.getExpiration());
    }

    public boolean validate(JsonWebToken jwt, String secretKey, String username) {
        if (jwt == null || secretKey == null || username == null) return false;

        byte[] secretKeyInBytes = Decoders.BASE64.decode(secretKey);
        Key hashedKey = Keys.hmacShaKeyFor(secretKeyInBytes);

        try {
            Optional<String> extractedUsername = extractUsername(jwt, hashedKey);
            if (extractedUsername.isEmpty()) return false;

            Optional<Date> extractedExpiration = extractExpiration(jwt, hashedKey);
            if (extractedExpiration.isEmpty()) return false;

            return (new Date().before(extractedExpiration.get())) &&
                    (username.equals(extractedUsername.get()));
        }
        catch (JwtException ex) {
            return false;
        }
    }

    public Optional<JsonWebToken> generateJWT(String username, String secretKey) {
        if (username == null || secretKey == null) return Optional.empty();

        byte[] secretKeyInBytes = Decoders.BASE64.decode(secretKey);
        Key hashedKey = Keys.hmacShaKeyFor(secretKeyInBytes);

        String bearer = Jwts.builder()
                .signWith(hashedKey, SignatureAlgorithm.HS256)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFESPAN))
                .compact();

        if (bearer == null) return Optional.empty();

        return Optional.of(new JsonWebToken(bearer));
    }
}
