package com.Museum.OnlineChatbotTicket.controller;


import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import com.Museum.OnlineChatbotTicket.repository.PersonalInfoRepository;
import com.Museum.OnlineChatbotTicket.repository.TicketDetailsRepository;
import com.Museum.OnlineChatbotTicket.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketDetailsRepository ticketRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{ticketId}/send-email/{email}")
    public String sendTicketEmail(@PathVariable Long ticketId, @PathVariable String email) {

        try {
            TicketDetails ticketDetails = ticketRepository.findById(ticketId).
                    orElseThrow(() -> new RuntimeException("Ticket not found"));

            PersonalInfo personalInfo = personalInfoRepository.findById(ticketDetails.getId()).
                    orElseThrow(() -> new RuntimeException("Personal Info not found"));

            emailService.sendTicketEmail(email, "Your Ticket Details", ticketDetails, personalInfo);
            return "Email sent successfully to: " + email;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }
    }
}
