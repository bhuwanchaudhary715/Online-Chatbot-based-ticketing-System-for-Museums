package com.Museum.OnlineChatbotTicket.controller;


import com.Museum.OnlineChatbotTicket.model.Orders;
import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import com.Museum.OnlineChatbotTicket.repository.OrdersRepository;
import com.Museum.OnlineChatbotTicket.repository.PersonalInfoRepository;
import com.Museum.OnlineChatbotTicket.repository.TicketDetailsRepository;
import com.Museum.OnlineChatbotTicket.service.EmailService;
import com.Museum.OnlineChatbotTicket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private TicketDetailsRepository ticketRepository;


    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/{ticketId}/send-email/{email}")
    public ResponseEntity<Map<String,Object>> sendTicketEmail(@PathVariable Long ticketId, @PathVariable String email) {

        Map<String, Object> response = new HashMap<>();

        try {

            System.out.println("Attempting to send email for ticket: " + ticketId);

            TicketDetails ticketDetails = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));
            System.out.println("Found ticket: " + ticketDetails);


            PersonalInfo personalInfo = personalInfoRepository.findById(ticketDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Personal Info not found"));
            System.out.println("Found personal info: " + personalInfo);


            Orders order = ordersRepository.findById(ticketDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            System.out.println("Found order: " + order);


            emailService.sendTicketEmail(email, "Your Ticket Details", ticketDetails, personalInfo, order);
            System.out.println("Email sent successfully");

            response.put("success", true);
            response.put("message", "Email sent successfully to: " + email);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace(); // This will show in server logs

            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{ticketId}")
    public ResponseEntity<Orders> getTicketDetails(@PathVariable Long ticketId) {

        Orders order = orderService.fetchTicketDetails(ticketId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }



}
