package com.user.userAuth.services;

import com.user.userAuth.models.Booking;
import com.user.userAuth.models.BookingDetail;
import com.user.userAuth.repositories.BookingDetailRepository;
import com.user.userAuth.repositories.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
    public class BookingService {


    @Autowired
    private BookingRepository bookingRepository; // Assuming you have a JPA repository

    @Autowired
    private BookingDetailRepository bookingDetailRepository;


    /**
     * Saves basic booking details.
     *
     * @param name    the customer's name
     * @param email   the customer's email
     * @param phoneNo the customer's phone number
     * @return true if save is successful
     */

    @Transactional
    public boolean saveBasicBookingDetails(String name, String email, String phoneNo) {
        try {
            Booking booking = new Booking();
            booking.setName(name);
            booking.setEmail(email);
            booking.setPhoneNo(phoneNo);
            bookingRepository.save(booking);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving basic booking details: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves ticket details.
     *
     * @param dateTime     the date and time of the booking
     * @param adultTickets number of adult tickets
     * @param childTickets number of child tickets
     * @return true if save is successful
     */
    @Transactional
    public boolean saveTicketDetails(String dateTime, String adultTickets, String childTickets) {

        try {
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setDateTime(dateTime);
            bookingDetail.setAdultTickets(adultTickets);
            bookingDetail.setChildTickets(childTickets);
            bookingDetailRepository.save(bookingDetail);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving ticket details: " + e.getMessage());
            return false;
        }
    }

}


    // ***********************************************************************************************

//    private BookingRepository_2 bookingRepository_2;
//
//
//    public boolean saveBookingDetails_2(String datetime, String adulttickets, String childtickets) {
//        try {
//
//            AdditionalDetails_2 booking2 = new AdditionalDetails_2();
//
//            booking2.setDatetime(datetime);
//            booking2.setAdultTickets(adulttickets);
//            booking2.setChildTickets(childtickets);
//
//            System.out.println("Booking saved successfully!");
//            bookingRepository_2.save(booking2);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error saving booking: " + e.getMessage());
//            return false;
//        }
//    }

