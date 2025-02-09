package com.Museum.OnlineChatbotTicket.service;

import com.Museum.OnlineChatbotTicket.Interface.BookingService;
import com.Museum.OnlineChatbotTicket.model.PersonalInfo;
import com.Museum.OnlineChatbotTicket.model.TicketDetails;
import com.Museum.OnlineChatbotTicket.repository.PersonalInfoRepository;
import com.Museum.OnlineChatbotTicket.repository.TicketDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // ← Mandatory annotation
public class BookingServiceImpl implements BookingService {

    private final PersonalInfoRepository personalInfoRepository;
    private final TicketDetailsRepository ticketDetailsRepository;

    @Autowired
    public BookingServiceImpl(PersonalInfoRepository personalInfoRepository, TicketDetailsRepository ticketDetailsRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.ticketDetailsRepository = ticketDetailsRepository;
    }


    @Override
    public boolean savePersonalInfo(PersonalInfo info) {

        try {
            personalInfoRepository.save(info);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean saveTicketDetails(TicketDetails details) {

        try {
            ticketDetailsRepository.save(details);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}