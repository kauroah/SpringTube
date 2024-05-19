package org.example.springtube.services;

import org.example.springtube.dto.UserDto;
import org.example.springtube.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    List<UserDto> search(Integer page, Integer size, String query, String sortParameter, String directionParameter);
}