package org.example.springtube.services;

import lombok.extern.slf4j.Slf4j;
import org.example.springtube.dto.UserDto;
import org.example.springtube.dto.UserForm;
import org.example.springtube.models.enums.Role;
import org.example.springtube.models.enums.State;
import org.example.springtube.models.User;
import org.example.springtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;
    @Autowired
    private SmsService smsService;

    private static final Logger logger = Logger.getLogger(SignUpServiceImpl.class.getName());

    @Override
    public void addUser(UserForm userForm) {
        if (userRepository.findByEmail(userForm.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        try {
            User user = User.builder()
                    .email(userForm.getEmail())
                    .password(passwordEncoder.encode(userForm.getPassword()))
                    .firstName(userForm.getFirstname())
                    .lastName(userForm.getLastname())
                    .phone(userForm.getPhone())
                    .age(userForm.getAge())
                    .state(State.NOT_CONFIRMED)
                    .role(Role.USER)
                    .confirmCode(UUID.randomUUID().toString())
                    .build();
            userRepository.save(user);
            smsService.sendSms(userForm.getPhone(), "You signed up!");
            log.info("User needs to confirm: {}", userForm.getEmail());
            mailService.sendEmailForConfirm(userForm.getEmail(), user.getConfirmCode());
            log.info("User signed up successfully: {}", userForm.getEmail());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while adding user.", e);
            throw new RuntimeException("Error occurred while adding user.");
        }
    }

    @Override
    public boolean confirmUser(String confirmCode) {
        try {
            Optional<User> option = userRepository.findByConfirmCode(confirmCode);
            if (option.isPresent()) {
                User user = option.get();
                user.setState(State.CONFIRMED);
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while confirming user.", e);
            throw new RuntimeException("Error occurred while confirming user.");
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return userRepository.findByEmail(username).orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while getting user by username.", e);
            throw new RuntimeException("Error occurred while getting user by username.");
        }
    }

    @Override
    public  void updateUserProfile(String email, String firstName, String lastName, String phone, String password) {
        userRepository.updateUserProfile(email, firstName, lastName, phone, passwordEncoder.encode(password));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getCurrentUserByEmail(String email){
        return userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found"));
    }
}