package org.example.dao;

import org.example.model.Movie;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface TheatreDao {
    Theatre findById(long theatreId);

    void update(Long theatreId, String name, String location);

    void delete(Long theatreId);

    List<Map<Movie, List<Theatre>>> getAllMoviesByTime(int hour);
}
