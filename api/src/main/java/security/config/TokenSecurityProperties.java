/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.token")
public class TokenSecurityProperties {
    /**
     * The secret.
     */
    private static String SECRET = "";

    /**
     * The cookie type.
     */
    public static String COOKIE_TYPE = "cookie";

    /**
     * The bearer type.
     */
    public static String BEARER_TYPE = "bearer";

    /**
     * The time the remember me cookie expires.
     */
    public static long REMEMBER_EXPIRATION_TIME =  864_000_000; // 10 days

    /**
     * The time the session cookie expires.
     */
    public static long SESSION_EXPIRATION_TIME =  86_400_000; // 1 day

    /**
     * The time the bearer token expires.
     */
    public static long BEARER_EXPIRATION_TIME =  864_000_000; // 10 days

    /**
     * The bearer token prefix.
     */
    public static String BEARER_TOKEN_PREFIX = "Bearer ";

    /**
     * The authorization header name.
     */
    public static String HEADER_NAME = "Authorization";

    /**
     * The authorisation cookie name.
     */
    public static String COOKIE_NAME = "AUTHORISATION";

    public String getSecret() {
        return SECRET;
    }

    public void setSecret(String secret) {
        SECRET = secret;
    }

    public String getCookieType() {
        return COOKIE_TYPE;
    }

    public void setCookieType(String cookieType) {
        COOKIE_TYPE = cookieType;
    }

    public String getBearerType() {
        return BEARER_TYPE;
    }

    public void setBearerType(String bearerType) {
        BEARER_TYPE = bearerType;
    }

    public long getRememberExpirationTime() {
        return REMEMBER_EXPIRATION_TIME;
    }

    public void setRememberExpirationTime(long rememberExpirationTime) {
        REMEMBER_EXPIRATION_TIME = rememberExpirationTime;
    }

    public long getSessionExpirationTime() {
        return SESSION_EXPIRATION_TIME;
    }

    public void setSessionExpirationTime(long sessionExpirationTime) {
        SESSION_EXPIRATION_TIME = sessionExpirationTime;
    }

    public long getBearerExpirationTime() {
        return BEARER_EXPIRATION_TIME;
    }

    public void setBearerExpirationTime(long bearerExpirationTime) {
        BEARER_EXPIRATION_TIME = bearerExpirationTime;
    }

    public String getBearerTokenPrefix() {
        return BEARER_TOKEN_PREFIX;
    }

    public void setBearerTokenPrefix(String bearerTokenPrefix) {
        BEARER_TOKEN_PREFIX = bearerTokenPrefix;
    }

    public String getHeaderName() {
        return HEADER_NAME;
    }

    public void setHeaderName(String headerName) {
        HEADER_NAME = headerName;
    }

    public String getCookieName() {
        return COOKIE_NAME;
    }

    public void setCookieName(String cookieName) {
        COOKIE_NAME = cookieName;
    }
}
