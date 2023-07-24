package org.example.services;

import org.example.model.Movie;
import org.example.model.ShowTime;
import org.example.model.Theatre;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ShowTimeService {
    String save(ShowTime showTime);

    String assign(Long showTimeId, Long movieId, Long theatreId);

    List<ShowTime> getAll();
    ShowTime getById(Long showTimeId);

    String deleteShowTimeByStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime);

    List<Map<Theatre, List<Movie>>> getMoviesGroupByTheater();}
