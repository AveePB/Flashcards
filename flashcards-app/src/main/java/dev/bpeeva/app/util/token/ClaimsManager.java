package dev.bpeeva.app.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class ClaimsManager {

    public static Optional<String> extractPrincipalName(String bearerToken, Key secrectKey) {
        String principalName = extractAllClaims(bearerToken, secrectKey).getSubject();

        return Optional.of(principalName);
    }

    public static Optional<Date> extractExpirationDate(String bearerToken, Key secrectKey) {
        Date expirationDate = extractAllClaims(bearerToken, secrectKey).getExpiration();

        return Optional.of(expirationDate);
    }

    private static Claims extractAllClaims(String bearerToken, Key secretKey) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(bearerToken)
                .getBody();
    }
}
