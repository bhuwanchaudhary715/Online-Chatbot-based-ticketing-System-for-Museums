package com.Museum.OnlineChatbotTicket.service;

import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import com.Museum.OnlineChatbotTicket.utility.QRCodeGenerator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTicketEmail(String toEmail, String subject, TicketDetails ticketDetails,
                                PersonalInfo personalInfo) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        // Generate QR Code content and image
        String qrCodeData = "Ticket ID: " + personalInfo.getId() +
                "\nName: " + personalInfo.getFirstname() + " " + personalInfo.getLastname() +
                "\nemail: " + personalInfo.getEmail() +
                "\nphone_no: " + personalInfo.getPhoneNo()+" "+
                "\ndate_of_birth: " + personalInfo.getDateofbirth()+" "+
                "\naddress: " + personalInfo.getAddress()+" "+
                "\nidentification_details: " + personalInfo.getIdentificationdetails();



        String qrCodeBase64 = QRCodeGenerator.generateQRCode(qrCodeData, 400, 400);

        // Build email content with embedded QR code image
        String emailContent = "<h1>Your Online Museum Ticket Details</h1>" +
                "<p>Ticket_ID No: " + ticketDetails.getId() + "</p>" +
                "<p>Name: " + personalInfo.getFirstname() + " " + personalInfo.getLastname() + "</p>" +
                "<p>Date: " + ticketDetails.getDate() + "</p>" +
                "<p>Time: " + ticketDetails.getTime() + "</p>" +
                "<p>Adult_ticket: " + ticketDetails.getAdultticket() + "</p>" +
                "<p>Child_ticket: " + ticketDetails.getChildticket() + "</p>" +
                "<img src='cid:qrCodeImage," + qrCodeBase64 + "' alt='QR Code'/>";

        helper.setText(emailContent, true); // Enable HTML content

        // Attach the QR code image
        ByteArrayResource qrCodeResource = new ByteArrayResource(java.util.Base64.getDecoder().decode(qrCodeBase64));
        helper.addInline("qrCodeImage", qrCodeResource, "image/png");

        mailSender.send(message);
    }
}
