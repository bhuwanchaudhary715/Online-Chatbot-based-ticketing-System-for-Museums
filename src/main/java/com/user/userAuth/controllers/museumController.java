package com.user.userAuth.controllers;

import com.user.userAuth.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class museumController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/dialogflow/webhook")
    public ResponseEntity<Map<String, Object>> handleDialogflowWebhook_1(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("Incoming Request: " + request);

            // Extract queryResult and parameters
            Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
            Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");
            String name = (String) parameters.get("name");
            String email = (String) parameters.get("email");
            String phoneNo = (String) parameters.get("phone-no");

            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Phone No: " + phoneNo);

            // Simulate save operation
            boolean isSaved = bookingService.saveBasicBookingDetails(name, email, phoneNo);
            if (isSaved) {
                System.out.println("Booking saved successfully!");

                // Fulfillment Text
                response.put("fulfillmentText", "Your details have been successfully saved.");

                // Custom Payload for Dialogflow Messenger
                response.put("payload", Map.of(
                        "richContent", List.of(List.of(
                                Map.of("type", "info", "subtitle", "Choose the below options Mr/Mrs. " + name + "."),
                                Map.of("type", "chips", "options", List.of(
                                        Map.of("text", "Book Tickets", "link", ""),
                                        Map.of("text", "Ticket Cancellation", "link", "")
                                ))
                        ))
                ));
            } else {
                System.err.println("Failed to save booking.");
                response.put("fulfillmentText", "There was an issue saving your details. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("fulfillmentText", "An error occurred while processing your request.");
        }
        return ResponseEntity.ok(response);
    }

}



