package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Exception.BadRequestException;
import com.pavel.newsweb.Service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:email.properties")
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void Send(String setto, String subject, String text) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(setto);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            try {
                javaMailSender.send(simpleMailMessage);
            }
            catch (MailException mailException){
                throw new BadRequestException("Error for send email: " + mailException.getMessage());
            }
    }
}
