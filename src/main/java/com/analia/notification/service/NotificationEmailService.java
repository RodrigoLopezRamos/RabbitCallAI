package com.analia.notification.service;

import com.analia.common.exception.AnaliaException;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.SettingsServiceLocal;
import com.analia.setttings.service.impl.SettingsService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

/**
 * Session Bean implementation class NoticationEmailCore
 */
@ApplicationScoped
public class NotificationEmailService implements NotificationEmailServiceLocal {

    private static final Log LOG = LogFactory.getLog(NotificationEmailService.class);

    @Inject
    private Mailer mailer;

    @Inject
    private SettingsService settingsServiceLocal;

    /**
     *
     */

    //  @Asynchronous
    public void sendEmail(String email, String subject, String body) throws AnaliaException {
        this.sendEmail(email, subject, body, "text/plain");
    }

    // @Asynchronous
    public void sendEmail(String email, String subject, String html, String mimeType) {
        try {
            mailer.send(Mail.withHtml(settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_CONFIRMATION_EMAIL_FROM), subject, html));
            LOG.info("Email was sent to email :" + email);
        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @param email
     * @param code
     * @param name
     * @throws AnaliaException
     */
    public void sendConfirmationEmail(String email, String code, String name) throws AnaliaException {
        String subject = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SUBJECT_TEMPLATE);
        String emailTemplate = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_EMAIL_TEMPLATE);
        String link = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SERVER_URL);

        link = link + "/rs/user/confirmEmail/" + code;

        emailTemplate = emailTemplate.replace("{$link}", link);
        emailTemplate = emailTemplate.replace("{$username}", name);

        this.sendEmail(email, subject, emailTemplate, "text/html");

    }


    /**
     * @param email
     * @param code
     * @param name
     * @throws AnaliaException
     */

    public void sendForgotPasswordEmail(String email, String code, String name) throws AnaliaException {
        String subject = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_FORGOT_PASSWORD_EMAIL_SUBJECT);
        String emailTemplate = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_FORGOT_PASSWORD_EMAIL_BODY);
        String host = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SERVER_URL);
        host = host + "/rs/user-profile/password-reset?resetCode=" + code;
        emailTemplate = emailTemplate.replace("{$code}", host);
        emailTemplate = emailTemplate.replace("{$username}", name);

        this.sendEmail(email, subject, emailTemplate, "text/html");

    }

}
