package org.example.springtube.services;

import org.example.springtube.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> findAllUsers();
    void blockUser(Long userId);
    void deleteUser(Long userId);
    void grantAdminRole(Long userId);
    void revokeAdminRole(Long userId);
    void unblockUser(Long userId);

    User findById(Long userId);

}


