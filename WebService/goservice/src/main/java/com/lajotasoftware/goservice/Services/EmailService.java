package com.lajotasoftware.goservice.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String para, String titulo, String conteudo) {
        log.info("Enviando email");

        var email = new SimpleMailMessage();

        email.setTo(para);

        email.setSubject(titulo);

        email.setText(conteudo);

        javaMailSender.send(email);
        log.info("Email enviado com sucesso!");
    }

    /*
    public void enviarEmailComAnexo(String para, String titulo, String conteudo, String arquivo) throws MessagingException {
        log.info("Enviando email com anexo");
        var mensagem = javaMailSender.createMimeMessage();

        var helper = new MimeMessageHelper(mensagem, true);

        helper.setTo(para);
        helper.setSubject(titulo);
        helper.setText(conteudo, true);

        helper.addAttachment("image1.jpeg", new ClassPathResource(arquivo));

        javaMailSender.send(mensagem);
        log.info("Email com anexo enviado com sucesso.");
    }
    */
}
