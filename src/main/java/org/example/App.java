package org.example;

import org.example.model.Booking;
import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.services.*;
import org.example.services.impl.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        MovieService movieService = new MovieServicesImpl();
        ShowTimeService showTimeService = new ShowTimeServiceImpl();
        ThetreService thetreService = new TheatreServicesImpl();
        UserService userService=new UserServiceImpl();
        BookingService bookingService=new BookingServiceImpl();
//        System.out.println(movieService.createMovie(
//               "booking",
//               List.of(
//                       "id serial primary key",
//                       "show_time_id int references show_time(id)",
//                       "user_id int references users(id)",
//                       "number_of_tickets varchar",
//                       "booking_time timestamp"
//               )
//        ));
        Scanner scannerForStr = new Scanner(System.in);
        Scanner scannerForNumber = new Scanner(System.in);

        while (true) {
            switch (new Scanner(System.in).nextLine()) {
                case "1", "save" -> {
                    System.out.println("Write title: ");
                    String title = scannerForStr.nextLine();
                    System.out.println("Write genre: ");
                    String genre = scannerForStr.nextLine();
                    System.out.println("Write duration: ");
                    int duration = scannerForNumber.nextInt();
                    System.out.println(movieService.saveMovie(new Movie(title, genre, duration)));
                }
                case "2", "find" -> {
                    System.out.println("Write movie id: ");
                    System.out.println(movieService.findByMovieId(scannerForNumber.nextLong()));
                }
                case "3" -> {
                    String save = showTimeService.save(new ShowTime(4L, 2L,
                            LocalDateTime.of(2023,
                                    7,
                                    20,
                                    18,
                                    30,
                                    0),
                            LocalDateTime.of(2023,
                                    7,
                                    20,
                                    20,
                                    30,
                                    0)
                    ));
                    System.out.println(save);

                }
                case "4" -> {
                    showTimeService.assign(9L, 3L, 2L);
                }
                case "5" -> {
                    System.out.println(movieService.findByMovieId(2L));
                }
                case "6" -> {
                    System.out.println(movieService.searcByName("Osman"));
                }
                case "7" -> {
                    System.out.println(movieService.getMoviesByGenre("comedy"));
                }
                case "8" -> {
                    System.out.println("Write asc and desc");
                    movieService.sortByDuration(scannerForStr.nextLine()).forEach(System.out::println);
                }
                case "9" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
                    LocalDateTime startTime = LocalDateTime.parse("2023-07-19 09:44:22.0000", formatter);
                    System.out.println(movieService.getMoviesByTheaterIdAndStartTime(2L, startTime));
                }
                case "10" -> {
                    thetreService.update(2L, "OshDram", "Osh");
                }
                case "11" -> {
                    thetreService.delete(1L);
                }
                case "12" -> {
                    thetreService.getAllMoviesByTime(13).forEach(System.out::println);
                }
                case "13" -> {
                    showTimeService.getAll().forEach(System.out::println);
                }
                case "14" -> {
                    System.out.println(showTimeService.getById(2L));
                }
                case "15" -> {
                    thetreService.findById(1L);
                }
                case "16" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    LocalDateTime startTime = LocalDateTime.parse("2023-07-19 09:44:22.000000", formatter);
                    LocalDateTime endTime = LocalDateTime.parse("2023-07-19 10:50:22.000000", formatter);

                    showTimeService.deleteShowTimeByStartAndEndTime(startTime, endTime);
                }
                case "17"->{
                    showTimeService.getMoviesGroupByTheater().forEach(System.out::println);
                }
                case "18"->{
                    userService.save("Beka","1234","beka@gmail.com");
                }
                case "19"->{
                    System.out.println(userService.findById(1L));
                }
                case "20"->{
                    System.out.println(userService.existByEmail("beka@gmail.com"));
                }
                case "21"->{
                    bookingService.save(new Booking(10L,1L,3));
                }
                case "22"->{
                    System.out.println(bookingService.findById(2L));
                }
                case "23"->{
                    bookingService.delete(1L);
                }
                case "24"->{
                    bookingService.getAllBookings().forEach(System.out::println);
                }
                case "25"->{
                    System.out.println(bookingService.getBookingByUserId(3L));
                }
            }
        }
    }
}
//    User methods:
//        CRUD
//        Boolean existByEmail(String email);

//        Booking methods:
//        String save(new Booking(Long showTimeId, Long userId, int numberOfTickets));
//        findById;
//        delete;
//        getAllBookings();
//        List<Booking> getBookingByUserId(Long userId);


//  ??? List<Movie> sortByDuration(String ascOrDesc);List<Movie>
//  ??? List<Movie> getMoviesByTheaterIdAndStartTime(Long theaterId, Timestamp startTime);