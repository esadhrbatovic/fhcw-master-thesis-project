package com.hrbatovic.micronaut.master.notification;

import jakarta.inject.Singleton;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Singleton
public class MailSender {

    private final Session session;

    public MailSender() {
        String host = "smtp.ionos.de";

        final String username = "g2c.admin@hrbatovic.com";
        final String password = "g2c.admin.123!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("g2c.admin@hrbatovic.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email Message Sent Successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
