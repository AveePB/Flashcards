package com.aveepb.flashcardapp.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonWebToken {

    private static final int TOKEN_LIFESPAN = 60 * 60 * 1000; //ONE HOUR

    /**
     * Checks the subject name and expiration date.
     * @param bearerToken the json web token.
     * @param username the username.
     * @param secretKey the signing key.
     * @return the boolean if valid otherwise false.
     */
    public static boolean validate(String bearerToken, String username, Key secretKey) {

        String extractedUsername = JsonWebTokenClaims.extractSubjectName(bearerToken, secretKey);
        Date extractedExpirationDate = JsonWebTokenClaims.extractExpirationDate(bearerToken, secretKey);

        return ((extractedUsername.equals(username)) && (new Date(System.currentTimeMillis()).before(extractedExpirationDate)));
    }

    /**
     * Generates a json web token using the HS256 signature algorithm.
     * @param extraClaims the token claims.
     * @param username the username.
     * @param secretKey  the signing key.
     * @return the json web token.
     */
    public static String generateToken(Map<String, Object> extraClaims, String username, Key secretKey) {

        return Jwts.builder()
                .setSubject(username)
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFESPAN))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a json web token using the HS256 signature algorithm.
     * @param username the username.
     * @param secretKey  the signing key.
     * @return the json web token.
     */
    public static String generateToken(String username, Key secretKey) {

        return JsonWebToken.generateToken(new HashMap<>(), username, secretKey);
    }
}
