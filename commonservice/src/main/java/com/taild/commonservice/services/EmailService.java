package com.taild.commonservice.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Send email with optional attachments
     * @param to email address
     * @param subject of the email
     * @param body of the email
     * @param isHtml true if email body is HTML
     * @param attachments optional attachments
     */


    public void sendEmail(String to, String subject, String body, boolean isHtml, File... attachments) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);


            if (attachments!= null) {
                for (File attachment : attachments) {
                    helper.addAttachment(attachment.getName(), attachment);
                }
            }


            mailSender.send(message);

            log.info("Email sent to: {}, subject: {}", to, subject);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());

            //TODO Handle retry logic
        }
    }

}
