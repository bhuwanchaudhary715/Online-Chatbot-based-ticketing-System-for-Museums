package com.user.userAuth.repositories;

import com.user.userAuth.models.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
    // You can define custom database queries here if needed
}