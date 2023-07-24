package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.BookingDao;
import org.example.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {
    private final Connection connection= JdbcConfig.getConnection();
    @Override
    public String save(Booking booking) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into booking(" +
                    "show_time_id, user_id, number_of_tickets)" +
                    "values(?,?,?)");
            preparedStatement.setLong(1,booking.getShowTimeId());
            preparedStatement.setLong(2,booking.getUserId());
            preparedStatement.setInt(3,booking.getNumberOfTickets());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Success saved";
    }

    @Override
    public Booking findById(Long bookingId) {
        Booking booking=new Booking();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * " +
                    "from booking " +
                    "where id=?");

            preparedStatement.setLong(1,bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                booking.setId(resultSet.getLong("id"));
                booking.setShowTimeId(resultSet.getLong("show_time_id"));
                booking.setUserId(resultSet.getLong("user_id"));
                booking.setNumberOfTickets(resultSet.getInt("number_of_tickets"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public String delete(Long bookingId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from booking where id=?");
            preparedStatement.setLong(1,bookingId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "delete";
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking>bookings=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * " +
                    "from booking");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                bookings.add(new Booking(
                        resultSet.getLong("id"),
                        resultSet.getLong("show_time_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("number_of_tickets"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    @Override
    public Booking getBookingByUserId(Long userId) {
        Booking booking=new Booking();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select b.* from " +
                    "booking b " +
                    "join users u on b.user_id=u.id " +
                    "where u.id=?");
            preparedStatement.setLong(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                booking.setId(resultSet.getLong("id"));
                booking.setShowTimeId(resultSet.getLong("show_time_id"));
                booking.setUserId( resultSet.getLong("user_id"));
                booking.setNumberOfTickets(resultSet.getInt("number_of_tickets"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }
}
