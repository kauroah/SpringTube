package org.example.springtube.services;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
    void sendPasswordResetEmail(String email, String token);
}
