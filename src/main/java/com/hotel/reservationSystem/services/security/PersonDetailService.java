package com.hotel.reservationSystem.services.security;

import com.hotel.reservationSystem.models.User;
import com.hotel.reservationSystem.models.security.PersonDetails;
import com.hotel.reservationSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public PersonDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exist!");
        }

        return new PersonDetails(user);
    }
}
