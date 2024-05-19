package org.example.springtube.services;

import org.example.springtube.repositories.PasswordResetTokenRepository;
import org.example.springtube.tokens.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PasswordServiceImpl implements PasswordService{
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Override
    public void save(PasswordResetToken resetToken) {
        tokenRepository.save(resetToken);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void delete(PasswordResetToken resetToken) {
        tokenRepository.deleteByToken(resetToken.getToken());
    }


}