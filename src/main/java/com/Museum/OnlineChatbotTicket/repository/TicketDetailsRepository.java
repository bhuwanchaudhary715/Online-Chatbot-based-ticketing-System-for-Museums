package com.Museum.OnlineChatbotTicket.repository;


import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



// TicketDetailsRepository.java
@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, Long> {

}