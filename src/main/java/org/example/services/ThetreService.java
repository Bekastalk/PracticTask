package org.example.services;

import org.example.model.Movie;
import org.example.model.Theatre;

import java.util.List;
import java.util.Map;

public interface ThetreService {

    void update(Long theatreId, String name, String location);

    void delete(Long theatreId);
    Theatre findById(long theatreId);
    List<Map<Movie, List<Theatre>>>  getAllMoviesByTime(int hour);
}
