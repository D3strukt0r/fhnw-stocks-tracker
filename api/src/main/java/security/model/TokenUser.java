/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

import static java.util.Collections.emptyList;

public class TokenUser extends User {
    /**
     * Whether to remember the login.
     */
    @Getter
    private String remember;

    /**
     * The user with token.
     *
     * @param username The username.
     * @param password The password.
     * @param remember Whether to remember.
     */
    @JsonCreator
    public TokenUser(
        @JsonProperty(value = "username", required = true) final String username,
        @JsonProperty(value = "password", required = true) final String password,
        @JsonProperty(value = "remember", required = true) final String remember
    ) {
        super(username, password, emptyList());
        this.remember = remember;
    }
}
