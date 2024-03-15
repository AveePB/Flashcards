package dev.bpeeva.app.bll.manager;

import dev.bpeeva.app.util.token.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {
    private static final String SECRET_KEY_256_BIT = "TELRMIx02DkZcBMvK1wj09z8RlTn5U+PR7WAPJ84eyJMWTnGYMlPtaEdLZbk0djpk79DyY3pHuKly6zMb5xFGd7eTfMB8yvSUK6JaJV7Jio1SuvcjxJtERtWfKemgLAX08NdGyB6pTiCB4tW2+J2nkWA1hJi09nlagigwHQK2kljZk7xIn+7IIAfIDfKBrqpar0MBut1YM7udhaegDubHO3l2ch2uhoxpVVLnwvSi2brMrzXucVxXMcT+4qkp0edH4yljdrMFMW0tKrPDzCoBtlmGAQUd+1birTC3X0C8SajKMOOvUXRELWjU7c2JLn2NMy8upajL8V/TKinjE0pOaW0ExnUhgHCnJG9MefoEfg=";
    private static final int TOKEN_LIFESPAN = 3600 * 1000; //one hour

    private static SecretKey getSecretKey() {
        byte[] keyInBytes = Decoders.BASE64.decode(JWTService.SECRET_KEY_256_BIT);

        return Keys.hmacShaKeyFor(keyInBytes);
    }

    public boolean validate(String username, JWT token) {

        return token.isValid(username, JWTService.getSecretKey());
    }

    public Optional<JWT> generateToken(String username) {
        if (username == null || username.isEmpty())
            return Optional.empty();

        String bearerToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWTService.TOKEN_LIFESPAN))
                .signWith(JWTService.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return Optional.of(new JWT(bearerToken));
    }
}
