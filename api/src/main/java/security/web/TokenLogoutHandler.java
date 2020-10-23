/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import security.config.TokenSecurityProperties;
import security.service.TokenService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenLogoutHandler implements LogoutHandler {
    /**
     * The token service.
     */
    private TokenService tokenService;

    /**
     * Constructor for the logout handler.
     *
     * @param tokenService The token service.
     */
    public TokenLogoutHandler(final TokenService tokenService) {
        super();
        this.tokenService = tokenService;
    }

    /**
     * Logout the user.
     */
    @Override
    public void logout(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Authentication authentication
    ) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(TokenSecurityProperties.COOKIE_NAME)) {
                    tokenService.blacklistToken(cookie.getValue());
                }
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }

        String token = request.getHeader(TokenSecurityProperties.HEADER_NAME);
        if (token != null) {
            token = token.replace(TokenSecurityProperties.BEARER_TOKEN_PREFIX, "");
            tokenService.blacklistToken(token);
        }
    }
}
