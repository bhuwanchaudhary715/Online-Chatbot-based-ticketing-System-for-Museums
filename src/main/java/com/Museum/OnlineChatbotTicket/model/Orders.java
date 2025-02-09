package com.Museum.OnlineChatbotTicket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "paymentorder")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long orderId;
    private String name;
    private String email;
    private Integer amount;
    private String orderStatus;
    private String razorpayOrderId;


}