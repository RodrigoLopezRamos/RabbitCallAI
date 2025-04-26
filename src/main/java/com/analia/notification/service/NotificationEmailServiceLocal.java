package com.analia.notification.service;

import com.analia.common.exception.AnaliaException;

public interface NotificationEmailServiceLocal {

    void sendEmail(String email, String subject, String  body, String mimeType) throws AnaliaException;

    void sendEmail(String email, String subject, String body) throws AnaliaException;

    void sendConfirmationEmail(String email, String code, String name) throws AnaliaException;

    void sendForgotPasswordEmail(String email, String code, String name) throws AnaliaException;


}
