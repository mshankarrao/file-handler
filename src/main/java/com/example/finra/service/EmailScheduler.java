package com.example.finra.service;

import com.example.finra.model.File;
import com.example.finra.repository.FileRepository;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Configuration
public class EmailScheduler {
    private static final String SMTP_SERVER = "smtp.gmail.com";

    private static final String EMAIL_FROM = "tester1test2020@gmail.com";
    private static final String EMAIL_TO = "tester1test2020@gmail.com";

    private static final String EMAIL_SUBJECT = "Hello, Email Scheduler";
    private static final String TESTER_1_TEST_2020 = "tester1test2020";
    private static final String TESTING_2020 = "testing2020!";


    @Autowired
    private FileRepository fileRepository;

//    @Scheduled(fixedDelay = 3600000,initialDelay = 3600000)
    @Scheduled(fixedDelay = 3600000,initialDelay = 9000)
    public void scheduler(){
        Date before = new Date(System.currentTimeMillis() - 3600 * 1000);
        List<File> files = fileRepository.findAllByDateIsAfter(before);

        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.user", TESTER_1_TEST_2020);
        prop.put("mail.smtp.password", TESTING_2020);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(prop, null);
        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(EMAIL_FROM));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));

           message.setSubject(EMAIL_SUBJECT);

            message.setText("Hello, ");
            message.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();

            for(File file : files){
                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setFileName(file.getName());
                ByteArrayDataSource bds = new ByteArrayDataSource(file.getData(), file.getContentType() );
                bodyPart.setDataHandler(new DataHandler(bds));
                multipart.addBodyPart(bodyPart);
            }

            message.setContent(multipart);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, TESTER_1_TEST_2020, TESTING_2020);

            // send
            t.sendMessage(message, message.getAllRecipients());
            System.out.println("sending");
            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
