package com.Museum.OnlineChatbotTicket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;


@Data
@Entity
@Table(name = "ticket_details")
public class TicketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time", nullable = false)
    private OffsetDateTime dateTime;

    @Column(name = "adult_tickets", nullable = false)
    private Integer adultTickets;

    @Column(name = "child_tickets", nullable = false)
    private Integer childTickets;

    // Getters, Setters, Constructors
}