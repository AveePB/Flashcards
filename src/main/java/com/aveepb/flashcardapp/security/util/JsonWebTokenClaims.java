package com.aveepb.flashcardapp.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;

public class JsonWebTokenClaims {

    /**
     * Fetches the subject claim.
     * @param token the json web token.
     * @param secretKey the secret key.
     * @return the subject name.
     */
    public static String extractSubjectName(String token, Key secretKey) {
        Claims claims = JsonWebTokenClaims.extractAllClaims(token, secretKey);

        return claims.getSubject();
    }

    /**
     * Fetches the expiration claim.
     * @param token the json web token.
     * @param secretKey the secret key.
     * @return the expiration date.
     */
    public static Date extractExpirationDate(String token, Key secretKey) {
        Claims claims = JsonWebTokenClaims.extractAllClaims(token, secretKey);

        return claims.getExpiration();
    }

    /**
     * Fetches the token's claims.
     * @param token the json web token.
     * @param secretKey the secret key.
     * @return the token claims.
     */
    private static Claims extractAllClaims(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
