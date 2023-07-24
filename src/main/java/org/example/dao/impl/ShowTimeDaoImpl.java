package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.ShowTimeDao;
import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTimeDaoImpl implements ShowTimeDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public ShowTime save(ShowTime showTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into show_time(movie_id, theatre_id, start_time, end_time)
                    values (?,?,?,?)
                    """);
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(showTime.getStartTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(showTime.getEndTime()));
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getShowTimeStartEnd(showTime.getStartTime(), showTime.getEndTime());
    }

    @Override
    public ShowTime getById(Long showTimeId) {
        ShowTime showTime = new ShowTime();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from show_time where id=?
                    """);
            preparedStatement.setLong(1, showTimeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                System.err.println("Movie with id " + showTimeId + " not found!!!");
            } else {
                showTime.setId(resultSet.getLong("id"));
                showTime.setMovieId(resultSet.getLong("movie_id"));
                showTime.setTheatreId(resultSet.getLong("theatre_id"));
                showTime.setStartTime(resultSet.getTimestamp("start_time").toLocalDateTime());
                showTime.setEndTime(resultSet.getTimestamp("end_time").toLocalDateTime());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTime;
    }

    @Override
    public void assign(ShowTime showTime) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update show_time set 
                    movie_id=?, theatre_id=?  where id=?
                    """);
            preparedStatement.setLong(3,showTime.getId());
            preparedStatement.setLong(1, showTime.getMovieId());
            preparedStatement.setLong(2, showTime.getTheatreId());
            preparedStatement.executeUpdate();
            System.out.println("good jub");
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShowTime> getAll() {
        List<ShowTime>showTimes=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from show_time");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ShowTime showTime=new ShowTime(
                        resultSet.getLong("id"),
               resultSet.getLong("movie_id"),
               resultSet.getLong("theatre_id"),
                resultSet.getTimestamp("start_time").toLocalDateTime(),
               resultSet.getTimestamp("end_time").toLocalDateTime()
                );
                showTimes.add(showTime);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTimes;
    }

    @Override
    public String deleteShowTimeByStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from show_time where start_time=? and end_time=?");
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endTime));
            preparedStatement.executeQuery();
           preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Deleted field ";
    }

    @Override
    public List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater() {
        List<Map<Theatre, List<Movie>>> moviesByTheater = new ArrayList<>();
        Map<Theatre, List<Movie>> theaterMoviesMap = new HashMap<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT t.id AS theater_id, t.name AS theater_name, t.location AS theater_location,
                       m.id AS movie_id, m.title AS movie_title, m.genre AS movie_genre, m.duration AS movie_duration
                FROM theatres t
                JOIN show_time st ON t.id = st.theatre_id
                JOIN movies m ON st.movie_id = m.id
                """);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long theaterId = resultSet.getLong("theater_id");
                String theaterName = resultSet.getString("theater_name");
                String theaterLocation = resultSet.getString("theater_location");

                Theatre theater = new Theatre(theaterId, theaterName, theaterLocation);

                long movieId = resultSet.getLong("movie_id");
                String movieTitle = resultSet.getString("movie_title");
                String movieGenre = resultSet.getString("movie_genre");
                int movieDuration = resultSet.getInt("movie_duration");

                Movie movie = new Movie(movieId, movieTitle, movieGenre, movieDuration);

                // Check if the theater is already in the map
                if (theaterMoviesMap.containsKey(theater)) {
                    theaterMoviesMap.get(theater).add(movie);
                } else {
                    List<Movie> movies = new ArrayList<>();
                    movies.add(movie);
                    theaterMoviesMap.put(theater, movies);
                }
            }

            // Add the theater-movie map to the list
            moviesByTheater.add(theaterMoviesMap);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return moviesByTheater;
    }


    public ShowTime getShowTimeStartEnd(LocalDateTime start, LocalDateTime end) {
        ShowTime showTime = new ShowTime();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                    select * from show_time where start_time=?  and end_time=?
                    """);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                showTime.setId(resultSet.getLong("id"));
                showTime.setMovieId(resultSet.getLong("movie_id"));

            } else {
                throw new RuntimeException("Not found show time");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showTime;
    }
}
