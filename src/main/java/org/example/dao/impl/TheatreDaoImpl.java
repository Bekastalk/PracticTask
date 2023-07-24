package org.example.dao.impl;

import org.example.config.JdbcConfig;
import org.example.dao.TheatreDao;
import org.example.model.Movie;
import org.example.model.Theatre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TheatreDaoImpl implements TheatreDao {
    private final Connection connection= JdbcConfig.getConnection();
    @Override
    public Theatre findById(long theatreId) {
        Theatre theatre=new Theatre();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select  * from theatres where id=?
                    """);
            preparedStatement.setLong(1,theatreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                theatre.setId(resultSet.getLong("id"));
                theatre.setName(resultSet.getString("name"));
                theatre.setLocation(resultSet.getString("location"));
            }else{
                throw  new RuntimeException(String.format("Theatre with id: %d not found!", theatreId));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return theatre;
    }

    @Override
    public void update(Long theatreId, String name, String location) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update theatres " +
                    " set name=?, location=? where id=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,location);
            preparedStatement.setLong(3, theatreId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Long theatreId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from theatres " +
                    "where id=? ");
            preparedStatement.setLong(1,theatreId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Map<Movie, List<Theatre>>> getAllMoviesByTime(int hour) {
        List<Map<Movie, List<Theatre>>> moviesByTime = new ArrayList<>();

        try {
            String sql = "select m.*, t.* " +
                    "from movies m " +
                    "join show_time st on m.id = st.movie_id " +
                    "join theatres t on st.theatre_id = t.id " +
                    "where exstract(hour from st.start_time) = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, hour);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getInt("duration")
                );

                Theatre theatre = new Theatre(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location")
                );

                Map<Movie, List<Theatre>> movieTheatreMap = new HashMap<>();
                movieTheatreMap.put(movie, Collections.singletonList(theatre));

                moviesByTime.add(movieTheatreMap);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return moviesByTime;
    }

}
