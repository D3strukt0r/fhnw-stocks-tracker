/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package fhnw.dreamteam.stockstracker.config;

import fhnw.dreamteam.stockstracker.data.seeddata.Seeder;
import fhnw.dreamteam.stockstracker.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.config.EnableTokenSecurity;
import security.service.TokenService;
import security.web.CSRFRequestMatcher;
import security.web.TokenAuthenticationFilter;
import security.web.TokenLoginFilter;
import security.web.TokenLogoutHandler;

@EnableWebSecurity
@EnableTokenSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * The user details.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * The password encode.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The token.
     */
    @Autowired
    private TokenService tokenService;

    /**
     * Configure the security policy.
     *
     * @param http The http instance.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).and()
            // If the X-Forwarded-Proto header is present, redirect to HTTPS (Heroku)
            .requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure().and()
            .csrf()
                .requireCsrfProtectionMatcher(new CSRFRequestMatcher())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            //.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/assets/**", "/login/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/user").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .antMatchers(HttpMethod.GET, "/logout").permitAll()
                .anyRequest().authenticated().and()
                    .addFilter(new TokenLoginFilter(authenticationManagerBean(), tokenService))
                    .addFilter(new TokenAuthenticationFilter(authenticationManagerBean(), tokenService))
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .addLogoutHandler(new TokenLogoutHandler(tokenService));
    }

    /**
     * Configure the authentication.
     *
     * @param auth The authentication instance.
     */
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * Get the authentication manager.
     *
     * @return Returns the authentication manager.
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
