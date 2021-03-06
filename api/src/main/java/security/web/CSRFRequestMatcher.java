/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.web;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;

public class CSRFRequestMatcher implements RequestMatcher {
    /**
     * The list of allowed methods.
     */
    private final HashSet<String> allowedMethods = new HashSet<>(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    /**
     * Check if request matches allowed methods.
     *
     * @param request The request.
     */
    @Override
    public boolean matches(final HttpServletRequest request) {
        if (this.allowedMethods.contains(request.getMethod()) || request.getCookies() == null) {
            return false;
        }
        return true;
    }
}
