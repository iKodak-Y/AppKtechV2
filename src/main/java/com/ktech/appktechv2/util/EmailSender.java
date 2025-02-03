package com.ktech.appktechv2.util;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

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

        // Enviar el correo
        Transport.send(msg);
        System.out.println("Correo enviado exitosamente a " + toAddress);
    }
}