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
    private EntityManager entityManagerSecurity;
    private UserDetailsService userDetailsService;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    private Key signingKey;

    @Autowired
    public TokenService(
        final EntityManager entityManagerSecurity,
        final UserDetailsService userDetailsServiceImpl,
        final TokenSecurityProperties tokenSecurityProperties
    ) {
        this.entityManagerSecurity = entityManagerSecurity;
        this.userDetailsService = userDetailsServiceImpl;
        this.signingKey = new SecretKeySpec(tokenSecurityProperties.getSecret().getBytes(), SIGNATURE_ALGORITHM.getJcaName());
    }

    public String issueToken(String subject, String type, Date expirationTime) {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expirationTime)
            .signWith(signingKey, SIGNATURE_ALGORITHM)
            .setHeaderParam("typ", type)
            .compact();
    }

    public String verifyAndGetSubject(final String token, final String type){
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

    private boolean isTokenBlacklisted(final String token) {
        return entityManagerSecurity.find(Token.class, token) != null;
    }

    public void blacklistToken(final String token) {
        if (!isTokenBlacklisted(token)) {
            entityManagerSecurity.getTransaction().begin();
            entityManagerSecurity.persist(new Token(token));
            entityManagerSecurity.getTransaction().commit();
        }
    }

}
