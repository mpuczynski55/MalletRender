package com.agh.mallet.domain.user.user.control.service;

import com.agh.mallet.infrastructure.exception.ExceptionType;
import com.agh.mallet.infrastructure.exception.MalletException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public static final String SENDING_EMAIL_ERROR_MSG = "Error occurred during sending email: ";
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(String subject, String recipient, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(content, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setFrom(senderEmail);
     //       mailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            throw new MalletException(SENDING_EMAIL_ERROR_MSG, ExceptionType.BAD_GATEWAY);
        }
    }

}
