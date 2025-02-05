package com.ktech.appktechv2.util;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private String host;
    private String port;
    private String username;
    private String password;

    public EmailSender(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachmentPath) throws MessagingException {
        logger.info("Intentando enviar correo a {}", toAddress);

        // Propiedades del servidor SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Crear una sesión autenticada
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(properties, auth);
        session.setDebug(true); // Habilita el debug de Jakarta Mail si lo necesitas

        // Crear el mensaje de correo
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
        msg.setSubject(subject);

        // Cuerpo del mensaje
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(message);

        // Adjuntar el archivo PDF
        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentPath);
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(new File(attachmentPath).getName());

        // Combinar el cuerpo del mensaje y el adjunto
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);

        msg.setContent(multipart);

        try {
            // Enviar el correo
            Transport.send(msg);
            logger.info("Correo enviado exitosamente a {}", toAddress);
        } catch (MessagingException e) {
            logger.error("Error al enviar el correo a {}", toAddress, e);
            throw e; // Re-lanza la excepción para que se maneje en otro lugar
        }
    }
}
