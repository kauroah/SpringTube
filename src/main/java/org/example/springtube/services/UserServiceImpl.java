package org.example.springtube.services;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.UserDto;
import org.example.springtube.models.enums.Role;
import org.example.springtube.models.enums.State;
import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
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

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }


    @Override
    public List<UserDto> search(Integer page, Integer size, String query, String sortParameter, String directionParameter) {
        Sort.Direction direction = Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "id");

        if (directionParameter != null) {
            direction = Sort.Direction.fromString(directionParameter);
        }

        if (sortParameter != null) {
            sort = Sort.by(direction, sortParameter);
        }

        if (query == null) {
            query = "empty";
        }

        if (size == null) {
            size = 3;
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> usersPage = userRepository.search(query, pageRequest);

        return UserDto.from(usersPage.getContent());
    }

}