package dev.bpeeva.app.util.token;

import lombok.Builder;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Builder
public record JWT(String bearerToken) {

    public boolean isValid(String username, Key secretKey) {
        if (this.bearerToken == null || this.bearerToken.isEmpty())
            return false;

        if (username == null || secretKey == null)
            return false;

        Optional<String> extractedUsername = ClaimsManager.extractPrincipalName(this.bearerToken, secretKey);
        if (extractedUsername.isEmpty()) return false;

        return (extractedUsername.get().equals(username) && !isExpired(secretKey));
    }

    private boolean isExpired(Key secretKey) {
        if (this.bearerToken == null || this.bearerToken.isEmpty() || secretKey == null)
            return false;

        Optional<Date> expirationDate = ClaimsManager.extractExpirationDate(this.bearerToken, secretKey);
        if (expirationDate.isEmpty()) return false;

        return expirationDate.get().before(new Date());
    }
}
