package dev.bpeeva.flashcardsapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class TokenClaims {

    /**
     * Fetches the expiration date from the token claims.
     * @param token the json web token.
     * @param key the secret key.
     * @return the optional object.
     */
    public static Optional<Date> extractExpirationDate(String token, Key key) {
        //Check token presence.
        if (token == null || token.equals(""))
            return Optional.empty();

        //Extract all claims.
        Claims claims = TokenClaims.extractAllClaims(token, key);

        //Fetch expiration date.
        return Optional.of(claims.getExpiration());
    }

    /**
     * Fetches the subject name from the token claims.
     * @param token the json web token.
     * @param key the secret key.
     * @return the optional object.
     */
    public static Optional<String> extractSubject(String token, Key key) {
        //Check token presence.
        if (token == null || token.equals(""))
            return Optional.empty();

        //Extract all claims.
        Claims claims = TokenClaims.extractAllClaims(token, key);

        //Fetch subject name.
        return Optional.of(claims.getSubject());
    }

    /**
     * Fetches all token claims.
     * @param token the json web token.
     * @param key the secret key.
     * @return the optional object.
     */
    public static Claims extractAllClaims(String token, Key key) {
        //Create new token and extract data.
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
