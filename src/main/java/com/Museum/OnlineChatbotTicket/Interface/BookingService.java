package com.Museum.OnlineChatbotTicket.Interface;

import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;


public interface BookingService {

    boolean savePersonalInfo(PersonalInfo info);

    boolean saveTicketDetails(TicketDetails details);

    TicketDetails getTicketById(Integer id);

    boolean updateTicketDetails(TicketDetails ticket);

    boolean deleteTicketById(Long id);
}