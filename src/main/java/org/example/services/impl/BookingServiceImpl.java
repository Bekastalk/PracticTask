package org.example.services.impl;

import org.example.dao.BookingDao;
import org.example.dao.impl.BookingDaoImpl;
import org.example.model.Booking;
import org.example.services.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {
    BookingDao bookingDao=new BookingDaoImpl();
    @Override
    public String save(Booking booking) {

        return bookingDao.save(booking);
    }

    @Override
    public Booking findById(Long bookingId) {
       return bookingDao.findById(bookingId);
    }

    @Override
    public String delete(Long bookingId) {
        return bookingDao.delete(bookingId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingDao.getAllBookings();
    }

    @Override
    public Booking getBookingByUserId(Long userId) {
        return bookingDao.getBookingByUserId(userId);
    }
}
