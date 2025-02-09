package com.Museum.OnlineChatbotTicket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "personal_info")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;


    // Getters, Setters, Constructors
}