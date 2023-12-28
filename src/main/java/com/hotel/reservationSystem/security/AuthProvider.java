package com.hotel.reservationSystem.security;

import com.hotel.reservationSystem.services.security.PersonDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class AuthProvider implements AuthenticationProvider {

    private final PersonDetailService personDetailService;

    @Autowired
    public AuthProvider(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails personDetails = personDetailService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("Bad Craditionals");
        }

        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}