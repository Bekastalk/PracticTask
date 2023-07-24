package org.example.services;

import org.example.model.Booking;

import java.util.List;

public interface BookingService {
    String save(Booking booking);

    Booking findById(Long bookingId);

    String delete(Long bookingId);

    List<Booking> getAllBookings();

    Booking getBookingByUserId(Long userId);
}
