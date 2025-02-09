package com.Museum.OnlineChatbotTicket.controller;


import com.Museum.OnlineChatbotTicket.Interface.BookingService;
import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dialogflow")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Map<String, Object>> handleBooking(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Map<String, Object> queryResult = (Map<String, Object>) request.get("queryResult");
            String intentName = (String) ((Map<String, Object>) queryResult.get("intent")).get("displayName");
            Map<String, Object> parameters = (Map<String, Object>) queryResult.get("parameters");

            // 👇 Add this logging line
            System.out.println("Received Intent: " + intentName);
            System.out.println("Received parameter: " + parameters);
            System.out.println("Received queryResult: " + queryResult);

            switch(intentName) {
                case "english":
                    handlePersonalInfo(parameters, response);
                    break;

                case "proceed with booking":
                    handleTicketDetails(parameters, response);
                    break;

                default:
                    response.put("fulfillmentText", "It looks like I need more details to assist you. Could you clarify your request?");
            }

        } catch (DateTimeParseException e) {
            return buildErrorResponse("Invalid date format. Use: YYYY-MM-DDTHH:MM:SS+ZZ:ZZ");
        } catch (NumberFormatException e) {
            return buildErrorResponse("Please enter valid numbers for tickets");
        } catch (Exception e) {
            return buildErrorResponse("Processing error: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
        
    }


    // -------------------  Handling the User Personal Details  ----------------------------------
    private void handlePersonalInfo(Map<String, Object> parameters, Map<String, Object> response) {
        List<String> requiredParams = Arrays.asList("name", "email", "phone-no");
        List<String> missing = validateParameters(parameters, requiredParams);

        if(!missing.isEmpty()) {
            response.put("fulfillmentText", "Missing: " + String.join(", ", missing));
            return;
        }

        PersonalInfo info = new PersonalInfo();
        info.setName(getStringParameter(parameters, "name"));
        info.setEmail(getStringParameter(parameters, "email"));
        info.setPhoneNo(parsePhoneNumber(parameters.get("phone-no")));

        boolean saved = bookingService.savePersonalInfo(info);
        response.put("fulfillmentText", saved ?
                "Your details have been successfully saved.\n"+
                        "\nChoose the below options Mr/Mrs "+info.getName()+
                        "\n1. Book Tickets" +
                        "\n2. Ticket Cancellation":
                "Failed to save information. Please try again.");
    }


    //  ------------------------------ Handling the User Tickets Details ----------------------------
    private void handleTicketDetails(Map<String, Object> parameters, Map<String, Object> response) {
        try {
            // Validate required parameters
            List<String> requiredParams = Arrays.asList("date-time", "adult-tickets", "child-tickets");
            List<String> missing = validateParameters(parameters, requiredParams);

            if(!missing.isEmpty()) {
                response.put("fulfillmentText", "❌ Missing: " + String.join(", ", missing));
                return;
            }

            // Create ticket
            TicketDetails details = new TicketDetails();
            details.setDateTime(parseDateTime(getStringParameter(parameters, "date-time")));
            details.setAdultTickets(getIntegerParameter(parameters, "adult-tickets"));
            details.setChildTickets(getIntegerParameter(parameters, "child-tickets"));

            // Save to DB
            boolean saved = bookingService.saveTicketDetails(details);

            response.put("fulfillmentText", saved ?
                    "✅ Booking confirmed!"+"" + "\nDo you have any Quries"+
                            "\n1. Yes"+
                            "\n2. No":
                    "❌ Failed to save booking");

        } catch (DateTimeParseException e) {
            response.put("fulfillmentText", "📅 Invalid date format. Use: YYYY-MM-DDTHH:MM:SS+ZZ:ZZ");
        } catch (NumberFormatException e) {
            response.put("fulfillmentText", "🔢 Please enter valid numbers");
        } catch (Exception e) {
            response.put("fulfillmentText", "⚠️ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Helper methods
    private List<String> validateParameters(Map<String, Object> parameters, List<String> requiredParams) {
        return requiredParams.stream()
                .filter(param -> parameters.get(param) == null)
                .collect(Collectors.toList());
    }

    private String getStringParameter(Map<String, Object> parameters, String paramName) {
        Object value = parameters.get(paramName);
        return value != null ? value.toString().trim() : null;
    }

    private Integer getIntegerParameter(Map<String, Object> parameters, String paramName) {
        Object value = parameters.get(paramName);
        if (value == null) return null;

        try {
            return value instanceof Double ?
                    ((Double) value).intValue() :
                    Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number for " + paramName);
        }
    }

    private String parsePhoneNumber(Object phoneNo) {
        if (phoneNo == null) return null;
        String number = phoneNo instanceof Double ?
                String.valueOf(((Double) phoneNo).longValue()) :
                phoneNo.toString();
        return number.replaceAll("[^0-9]", "");
    }

    private OffsetDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) return null;
        return OffsetDateTime.parse(dateTimeStr);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("fulfillmentText", message);
        return ResponseEntity.ok(response);
    }
}