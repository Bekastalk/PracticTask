package org.example.services;

import org.example.model.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MovieService {
    Movie movie=new Movie();
    String createMovie(String tableName, List<String> columns);

    String saveMovie(Movie movie);

    Movie findByMovieId(long id);

    List<Movie> searcByName(String title);

    Map<Movie, List<Movie>> getMoviesByGenre(String genre);

   List<Movie> sortByDuration(String ascAndDesc);

    List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime);
}
