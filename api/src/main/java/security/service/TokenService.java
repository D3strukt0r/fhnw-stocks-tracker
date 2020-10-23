/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import security.model.Token;
import security.config.TokenSecurityProperties;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {
    /**
     * The entity manager for security.
     */
    private EntityManager entityManagerSecurity;

    /**
     * The user details service.
     */
    private UserDetailsService userDetailsService;

    /**
     * The algorithm to use for the JWT token.
     */
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * The key.
     */
    private Key signingKey;

    /**
     * Create a token.
     *
     * @param entityManagerSecurity The entity manager.
     * @param userDetailsServiceImpl The user details.
     * @param tokenSecurityProperties The token properties.
     */
    @Autowired
    public TokenService(
        final EntityManager entityManagerSecurity,
        final UserDetailsService userDetailsServiceImpl,
        final TokenSecurityProperties tokenSecurityProperties
    ) {
        this.entityManagerSecurity = entityManagerSecurity;
        this.userDetailsService = userDetailsServiceImpl;
        this.signingKey = new SecretKeySpec(
            tokenSecurityProperties.getSecret().getBytes(),
            SIGNATURE_ALGORITHM.getJcaName()
        );
    }

    /**
     * Create a new token.
     *
     * @param subject        The subject.
     * @param type           The type of the token.
     * @param expirationTime The time the token expires.
     *
     * @return Returns the token.
     */
    public String issueToken(final String subject, final String type, final Date expirationTime) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expirationTime)
            .signWith(signingKey, SIGNATURE_ALGORITHM)
            .setHeaderParam("typ", type)
            .compact();
    }

    /**
     * Verify the token and return the subject.
     *
     * @param token The token.
     * @param type  The type of the token.
     *
     * @return Returns the subject or null.
     */
    public String verifyAndGetSubject(final String token, final String type) {
        if (this.isTokenBlacklisted(token)) {
            return null;
        }

        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token);

        if (claims != null) {
            if (type != null && !((String) claims.getHeader().get("typ")).equals(type)) {
                return null;
            }
            String username = claims.getBody().getSubject();
            if (this.userDetailsService.loadUserByUsername(username) != null) {
                return username;
            }
        }
        return null;
    }

    /**
     * Whether the token has been blacklisted.
     *
     * @param token The token.
     *
     * @return Returns true or false correspondingly.
     */
    private boolean isTokenBlacklisted(final String token) {
        return entityManagerSecurity.find(Token.class, token) != null;
    }

    /**
     * Blacklist a token.
     *
     * @param token The token.
     */
    public void blacklistToken(final String token) {
        if (!isTokenBlacklisted(token)) {
            entityManagerSecurity.getTransaction().begin();
            entityManagerSecurity.persist(new Token(token));
            entityManagerSecurity.getTransaction().commit();
        }
    }
}
