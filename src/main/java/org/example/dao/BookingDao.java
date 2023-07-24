package org.example.dao;

import org.example.model.Booking;

import java.util.List;

public interface BookingDao {
    String save(Booking booking);

    Booking findById(Long bookingId);

    String delete(Long bookingId);

    List<Booking> getAllBookings();

    Booking getBookingByUserId(Long userId);
}
