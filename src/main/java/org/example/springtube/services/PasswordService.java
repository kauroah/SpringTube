package org.example.springtube.services;

import org.example.springtube.tokens.PasswordResetToken;

import java.util.Optional;

public interface PasswordService {

    void save(PasswordResetToken resetToken);

    Optional<PasswordResetToken> findByToken(String token);

    void delete(PasswordResetToken resetToken);
}