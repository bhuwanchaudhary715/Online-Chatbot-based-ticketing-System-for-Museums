package com.Museum.OnlineChatbotTicket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "personal_info")
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "date_of_birth")
    private LocalDate dateofbirth;

    @Column(name = "address")
    private String address;

    @Column(name = "identification_details")
    private String identificationdetails;

    // Getters and Setters

}