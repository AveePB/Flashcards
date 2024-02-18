package dev.bpeeva.flashcardsapp.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String SECRET_KEY_256_BIT = "5ZYb6tZ9S76wVSNOEL+NJVCD9mbTVM+/QwXuZohaJrt0EGYhaCu2TSZNcenuaHID";
    private static final int TOKEN_LIFESPAN = 3600 * 1000; //ONE HOUR

    /**
     * Creates a signing key object from a given secret key.
     * @return the signing key.
     */
    public static Key getSigningKey() {
        //Decode string.
        byte[] decodedKey = Decoders.BASE64.decode(SECRET_KEY_256_BIT);

        return Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Creates a new validation token (JWT) for a user.
     * @param user the user details.
     * @param key the secret key.
     * @param extraClaims the extra token claims.
     * @return the token.
     */
    public Token generateJWT(UserDetails user, Key key, HashMap<String, Object> extraClaims) {
        //Check presence.
        if (user == null || key == null) return new Token();

        //Create json web token.
        return new Token(Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFESPAN))
                .compact()
        );
    }

    /**
     * Creates a new validation token (JWT) for a user.
     * @param user the user details.
     * @param key the secret key.
     * @return the token.
     */
    public Token generateJWT(UserDetails user, Key key) {
        //Call function.
        return generateJWT(user, key, new HashMap<>());
    }
}
