package com.phanlop.khoahoc.Service.implementation;

import com.phanlop.khoahoc.Service.EmailServices;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServicesImpl implements EmailServices {
    private final JavaMailSender javaMailSender;

    @Override
    public boolean sendOTPEmail(String email, String title, String body) {
        try {
            // Tạo một MimeMessage để gửi email
            MimeMessage message = javaMailSender.createMimeMessage();

            // Sử dụng MimeMessageHelper để cấu hình email
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);        // Địa chỉ email người nhận
            helper.setSubject(title);   // Tiêu đề email
            helper.setText(body, true); // Nội dung email với HTML

            // Gửi email
            javaMailSender.send(message);

            return true;
        } catch (MailException | MessagingException ex) {
            // Xử lý lỗi gửi email
            ex.printStackTrace();
            return false;
        }
    }
}
