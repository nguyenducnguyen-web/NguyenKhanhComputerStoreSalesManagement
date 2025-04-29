/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * 
 * @author nguyenducnguyen
 */
public class GuiEmail {

    public static void GuiOTP(String emailTo, String otp) {
        // Thông tin tài khoản gửi email
        String username = "cuahangmaytinhnguyenkhanh@gmail.com";
        String password = "zsyy yhzp xtxo snzn"; // App Password của Gmail

        // Cấu hình properties cho Gmail SMTP
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        // Tạo session để gửi email
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Tạo email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject("Đặt lại mật khẩu tài khoản nhân viên");

            // Tạo nội dung email dạng HTML
            String htmlContent = "<!DOCTYPE html>" +
                    "<html lang='vi'>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "<title>Đặt lại mật khẩu</title>" +
                    "</head>" +
                    "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f6f6f6;'>" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='max-width: 600px; background-color: #ffffff; margin: 40px auto; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);'>" +
                    "<tr>" +
                    "<td style='background-color: #1877F2; text-align: center; padding: 20px; border-top-left-radius: 8px; border-top-right-radius: 8px;'>" +
                    "<h1 style='color: #ffffff; margin: 0; font-size: 24px; font-weight: bold;'>Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh</h1>" +
                    "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='padding: 40px 30px;'>" +
                    "<h2 style='color: #1c2526; font-size: 20px; margin: 0 0 10px 0;'>Yêu cầu đặt lại mật khẩu</h2>" +
                    "<p style='color: #606770; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;'>Gửi nhân viên,</p>" +
                    "<p style='color: #606770; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;'>Xin chào, mình là Nguyễn Đức Nguyên (admin của chương trình). Mình đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh. Để tiếp tục, xin vui lòng sử dụng mã xác nhận dưới đây:</p>" +
                    "<div style='text-align: center; margin: 30px 0;'>" +
                    "<span style='display: inline-block; background-color: #e7f3ff; color: #1877F2; font-size: 24px; font-weight: bold; padding: 10px 20px; border-radius: 4px; letter-spacing: 2px;'>" + otp + "</span>" +
                    "</div>" +
                    "<p style='color: #606770; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;'>Vui lòng nhập mã này vào giao diện khôi phục mật khẩu để tiến hành đặt lại mật khẩu. Mã này có hiệu lực trong vòng 10 phút.</p>" +
                    "<p style='color: #606770; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;'>Nếu bạn không thực hiện yêu cầu này, xin vui lòng bỏ qua email này. Tài khoản của bạn vẫn an toàn.</p>" +
                    "<p style='color: #606770; font-size: 16px; line-height: 24px; margin: 0;'>Trân trọng,<br>Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh</p>" +
                    "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style='background-color: #f6f6f6; text-align: center; padding: 20px; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>" +
                    "<p style='color: #90949c; font-size: 12px; margin: 0;'>© 2025 Chương trình quản lý bán hàng - cửa hàng máy tính Nguyễn Khanh. Mọi quyền được bảo lưu.</p>" +
                    "<p style='color: #90949c; font-size: 12px; margin: 5px 0 0 0;'>Nếu có thắc mắc, vui lòng liên hệ Nguyễn Đức Nguyên: <a href='mailto:cuahangmaytinhnguyenkhanh@gmail.com' style='color: #1877F2; text-decoration: none;'>cuahangmaytinhnguyenkhanh@gmail.com</a></p>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</body>" +
                    "</html>";

            // Tạo MimeMultipart để hỗ trợ nội dung HTML
            MimeMultipart multipart = new MimeMultipart();

            // Tạo phần nội dung HTML
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
            multipart.addBodyPart(htmlPart);

            // Đặt nội dung cho email
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}