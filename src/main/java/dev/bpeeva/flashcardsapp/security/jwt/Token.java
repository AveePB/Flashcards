package dev.bpeeva.flashcardsapp.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String bearerToken;

    /**
     * Validates the bearer token.
     * @param user the user details.
     * @param key the secret key.
     * @return boolean value.
     */
    public boolean isValid(UserDetails user, Key key) {
        //Check bearer token.
        if (this.bearerToken == null || this.bearerToken.equals(""))
            return false;

        //Check username.
        Optional<String> username = getSubject(key);
        if (username.isEmpty()) return false;

        if (!username.get().equals(user.getUsername()))
            return false;

        //Check expiration date.
        return !isExpired(key);
    }

    /**
     * Validates the expiration date.
     * @param key the secret key.
     * @return boolean value.
     */
    public boolean isExpired(Key key) {
        //Check bearer token.
        if (this.bearerToken == null || this.bearerToken.equals(""))
            return false;

        try {
            //Extract expiration date.
            Optional<Date> expirationDate = TokenClaims.extractExpirationDate(this.bearerToken, key);

            //Check expiration date.
            return expirationDate.map(date -> date.before(new Date())).orElse(false);
        }
        catch (ExpiredJwtException ex) {
            return true;
        }
    }

    /**
     * Fetches the subject name.
     * @return the optional object.
     */
    public Optional<String> getSubject(Key key) {
        //Extract username.
        return TokenClaims.extractSubject(this.bearerToken, key);
    }
}
