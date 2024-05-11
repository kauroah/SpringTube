package org.example.springtube.services;

import org.example.springtube.models.Role;
import org.example.springtube.models.State;
import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    //private static final int CONFIRMATION_CODE_LENGTH = 6;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;


    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = findUserById(userId);
        user.setState(State.BLOCKED);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void grantAdminRole(Long userId) {
        User user = findUserById(userId);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void revokeAdminRole(Long userId) {
        User user = findUserById(userId);
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void unblockUser(Long userId) {
        User user = findUserById(userId);
        user.setState(State.NOT_CONFIRMED);
        mailService.sendEmailForConfirm(user.getEmail(), user.getConfirmCode());
        userRepository.save(user);
    }
}
