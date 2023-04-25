package com.emontazysta.service.impl;

import com.emontazysta.model.EmailData;
import com.emontazysta.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String from;

    @Override
    public void sendEmail(EmailData details) {
        try {
            MimeMessage htmlMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(htmlMessage, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(details.getTo());
            messageHelper.setText(details.getMessage(), true);
            messageHelper.setSubject(details.getSubject());

            javaMailSender.send(htmlMessage);

            log.info("E-mail sent successfully!");
        }catch (Exception e) {
            log.error("Error while sending e-mail!");
        }
    }
}
