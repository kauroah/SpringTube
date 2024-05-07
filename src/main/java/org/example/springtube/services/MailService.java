package org.example.springtube.services;

public interface MailService {
    void sendEmailForConfirm(String email, String code);
}
