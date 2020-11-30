/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import security.config.TokenSecurityProperties;
import security.model.TokenUser;
import security.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * The token service.
     */
    private TokenService tokenService;

    /**
     * The user.
     */
    private TokenUser user = null;

    /**
     * The login filter.
     *
     * @param authenticationManager The authentication manager.
     * @param tokenService The token service.
     */
    public TokenLoginFilter(
        final AuthenticationManager authenticationManager,
        final TokenService tokenService
    ) {
        super.setAuthenticationManager(authenticationManager);
        this.tokenService = tokenService;
    }

    /**
     * Attempt an authentication.
     *
     * @param request  The request.
     * @param response The response.
     *
     * @return Returns the authenticated object with the credentials.
     */
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            this.user = new ObjectMapper().readValue(request.getInputStream(), TokenUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword())
        );
    }

    /**
     * What to do on a successful authentication.
     *
     * @param request  The request.
     * @param response The response.
     * @param chain    The filter chain.
     * @param auth     The authentication.
     */
    @Override
    protected void successfulAuthentication(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain chain,
        final Authentication auth
    ) throws IOException, ServletException {
        Date date = null;
        Cookie cookie = null;

        if (Boolean.parseBoolean(this.user.getRemember())) {
            date = new Date(System.currentTimeMillis() + TokenSecurityProperties.REMEMBER_EXPIRATION_TIME);
        } else {
            date = new Date(System.currentTimeMillis() + TokenSecurityProperties.SESSION_EXPIRATION_TIME);
        }
        String cookieToken = this.tokenService.issueToken(
            this.user.getUsername(),
            TokenSecurityProperties.COOKIE_TYPE, date
        );
        cookie = new Cookie(TokenSecurityProperties.COOKIE_NAME, cookieToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        if (Boolean.parseBoolean(this.user.getRemember())) {
            cookie.setMaxAge(Math.toIntExact(TokenSecurityProperties.REMEMBER_EXPIRATION_TIME / 1000));
        }
        response.addCookie(cookie);

        date = new Date(System.currentTimeMillis() + TokenSecurityProperties.BEARER_EXPIRATION_TIME);
        String bearerToken = this.tokenService.issueToken(
            this.user.getUsername(),
            TokenSecurityProperties.BEARER_TYPE, date
        );
        response.addHeader(
            TokenSecurityProperties.HEADER_NAME,
            TokenSecurityProperties.BEARER_TOKEN_PREFIX + bearerToken
        );
    }
}
