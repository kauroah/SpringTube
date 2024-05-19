package org.example.springtube.services;

public interface SmsService {
    void sendSms(String phone, String text);
}