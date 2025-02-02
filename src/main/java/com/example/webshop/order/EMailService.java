package com.example.webshop.order;

import org.springframework.stereotype.Service;

@Service
public class EMailService {
    public void sendEmail(Long userId) {
        System.out.println("Sending email to user with ID: " + userId);
    }
}
