package org.example.springtube.security.details;

import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import org.example.springtube.models.enums.State;

@Component("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the user's account is active (e.g., not banned or deleted)
            if (user.getState() == State.CONFIRMED) {
                return new UserDetailsImpl(user);
            } else {
                // If the user's account is inactive, you can throw an appropriate exception
                throw new UsernameNotFoundException("User account is not confirmed");
            }
        } else {
            // If the user with the provided email is not found, throw UsernameNotFoundException
            throw new UsernameNotFoundException("User not found");
        }
    }
}