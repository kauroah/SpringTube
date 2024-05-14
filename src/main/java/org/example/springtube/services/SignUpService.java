package org.example.springtube.services;

import org.example.springtube.dto.UserForm;
import org.example.springtube.models.User;

import java.util.Optional;

public interface SignUpService {
    void addUser(UserForm userForm);
    boolean confirmUser(String confirmCode);
    User getUserByUsername(String username);
    void updateUserProfile(String email, String firstName, String lastName, String phone, String password);

    User findById(Long userId);

    User authenticateAndGetUserId(String email, String password);

    Optional<User> findByEmail(String email);

    void save(User user);

}
