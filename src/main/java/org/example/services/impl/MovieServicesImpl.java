package org.example.services.impl;

import org.example.dao.MovieDao;
import org.example.dao.impl.MovieDaoImpl;
import org.example.model.Movie;
import org.example.services.MovieService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MovieServicesImpl implements MovieService {
    MovieDao movieDao=new MovieDaoImpl();
    @Override
    public String createMovie(String tableName, List<String> columns) {
        movieDao.createTable(tableName,columns);
        return "Successfully created table with name "+ tableName;
    }

    @Override
    public String saveMovie(Movie movie) {
        movieDao.save(movie);
        return "Success saved";
    }

    @Override
    public Movie findByMovieId(long id) {
        return movieDao.findById(id);
    }

    @Override
    public List<Movie> searcByName(String title) {
        return movieDao.searcByName(title);
    }

    @Override
    public Map<Movie, List<Movie>> getMoviesByGenre(String genre) {

        return movieDao.getMoviesByGenre(genre);
    }

    @Override
    public List<Movie> sortByDuration(String ascAndDesc) {

        return movieDao.sortByDuration(ascAndDesc);
    }

    @Override
    public List<Movie> getMoviesByTheaterIdAndStartTime(Long theatreId, LocalDateTime startTime) {

        return movieDao.getMoviesByTheaterIdAndStartTime(theatreId,startTime);
    }
}
