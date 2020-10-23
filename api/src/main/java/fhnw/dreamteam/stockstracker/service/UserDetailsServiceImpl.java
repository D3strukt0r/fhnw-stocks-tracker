/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package fhnw.dreamteam.stockstracker.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Get a user by it's username.
     *
     * @param username The username.
     *
     * @return Returns the user (TODO)
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        /*Agent agent = agentRepository.findByEmail(username);
        if (agent == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(agent.getEmail(), agent.getPassword(), emptyList());*/
        return null;
    }
}
