package com.kafka.consumer.service;

import com.rinkul.avro.schema.EmployeeRecord;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailToEmployee(EmployeeRecord employee, String subject) throws MessagingException, IOException {

        String updateLink = String.format(
                "http://localhost:8082/update-employee.html?firstName=%s&lastName=%s&email=%s&empId=%d",
                URLEncoder.encode(employee.getFirstName().toString(), StandardCharsets.UTF_8),
                URLEncoder.encode(employee.getLastName().toString(), StandardCharsets.UTF_8),
                URLEncoder.encode(employee.getEmail().toString(), StandardCharsets.UTF_8),
                employee.getEmpId()
        );


        var htmlBody = getEmailBody(employee.getFirstName().toString(), updateLink);
        var message = javaMailSender.createMimeMessage();
        var simpleMailMessage = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        simpleMailMessage.setFrom("hrcore.europe@gmail.com");
        simpleMailMessage.setTo(employee.getEmail().toString());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    private String getEmailBody(String name, String updateLink) throws IOException {
        // Load email template
        var templateResource = new ClassPathResource("templates/email-template.html");
        var template = StreamUtils.copyToString(templateResource.getInputStream(), StandardCharsets.UTF_8);

        // Replace placeholders with actual values
        template = template.replace("{{name}}", name);
        template = template.replace("{{updateLink}}", updateLink);

        return template;
    }
}
