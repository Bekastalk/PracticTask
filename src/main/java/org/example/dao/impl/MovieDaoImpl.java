package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.MovieDao;
import org.example.model.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDaoImpl implements MovieDao {
    private final Connection connection = JdbcConfig.getConnection();

    @Override
    public void createTable(String tableName, List<String> columns) {
        StringBuilder stringBuilder = new StringBuilder(String.format("create table %s(", tableName));
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < columns.size(); i++) {
                stringBuilder.append(columns.get(i));
                if (i < columns.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")");
            statement.executeUpdate(stringBuilder.toString());
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void save(Movie movie) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into movies(title, genre, duration)
                    values(?,?,?)
                    """);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setInt(3, movie.getDuration());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Movie findById(Long id) {
        Movie movie = new Movie();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from movies where id=?
                    """);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Movie with id " + id + " not found!!!");
            } else {
                movie.setId(resultSet.getLong("id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setGenre(resultSet.getString("genre"));
                movie.setDuration(resultSet.getInt("duration"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    @Override
    public List<Movie> searcByName(String title) {
        Movie movie = new Movie();
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from movies where title=?
                    """);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                movies.add(new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                ));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    @Override
    public Map<Movie, List<Movie>> getMoviesByGenre(String genre) {
        Map<Movie, List<Movie>> byGenre = new HashMap<>();
        List<Movie> movies = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from movies where  genre=?
                    """);
            preparedStatement.setString(1, genre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getString("genre")
                );
                movies.add(new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                ));
                byGenre.put(movie, movies);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return byGenre;
    }

    @Override
    public List<Movie> sortByDuration(String ascAndDesc) {
        List<Movie>movies=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from movies  order by duration " +ascAndDesc);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Movie movie=new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                );
                movies.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;

    }

    @Override
    public List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime) {
        List<Movie>movies=new ArrayList<>();
        Movie movie=new Movie();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select m.* " +
                    "from show_time st " +
                    "join movies m on st.movie_id=m.id " +
                    "join theatres t on st.theatre_id=t.id " +
                    "where t.id=? and st.start_time=?");
            preparedStatement.setLong(1,theatreId);
            preparedStatement.setTimestamp(2,Timestamp.valueOf(startTime));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                movie=new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                );
                movies.add(movie);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }
}
