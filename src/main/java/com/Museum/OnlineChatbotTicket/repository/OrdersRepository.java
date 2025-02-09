package com.Museum.OnlineChatbotTicket.repository;

import com.Museum.OnlineChatbotTicket.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{

    Orders findByRazorpayOrderId(String razorpayId);

}