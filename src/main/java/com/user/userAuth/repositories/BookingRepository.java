package com.user.userAuth.repositories;

import com.user.userAuth.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // You can define custom database queries here if needed

}