package com.mateuspaz.enviosmtp.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mateuspaz.enviosmtp.domain.Email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/send")
public class SenderController {

    @Autowired
    private JavaMailSender emailSender;
    @Value("${email.from.username}")
    private String emailFrom;

    @PostMapping
    public ResponseEntity<String> enviarEmail(@RequestBody Email email) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailFrom);

            helper.setTo(email.emailTo());
            helper.setSubject(email.assunto());
            helper.setText(email.mensagem());

            emailSender.send(message);

            return ResponseEntity.ok("E-mail enviado com sucesso!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
