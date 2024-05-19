package org.example.springtube.security.details;

import org.example.springtube.models.User;
import org.example.springtube.models.enums.State;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    // The User object representing the user details
    private User user;

    // Constructor to initialize the UserDetailsImpl with a User object
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    // Returns the authorities granted to the user. In this case, it returns the user's role.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Wraps the user's role into a SimpleGrantedAuthority and returns it as a singleton collection
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));
    }

    // Returns the password of the user
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Returns the username of the user, which in this case is the user's email
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Indicates whether the user's account has expired. Returns true as all accounts are non-expired by default.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indicates whether the user's account is locked. Returns true as all accounts are non-locked by default.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indicates whether the user's credentials (password) have expired. Returns true as all credentials are non-expired by default.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indicates whether the user's account is enabled. It checks if the user's state is CONFIRMED.
    @Override
    public boolean isEnabled() {
        // The account is enabled only if the user's state is CONFIRMED
        return user.getState() == State.CONFIRMED;
    }

    // Returns the user's ID
    public Long getUserId() {
        return user.getId();
    }
}