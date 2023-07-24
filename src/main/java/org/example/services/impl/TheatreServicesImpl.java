package org.example.services.impl;

import org.example.dao.TheatreDao;
import org.example.dao.impl.TheatreDaoImpl;
import org.example.model.Movie;
import org.example.model.Theatre;
import org.example.services.ThetreService;

import java.util.List;
import java.util.Map;

public class TheatreServicesImpl implements ThetreService {
    TheatreDao theatreDao=new TheatreDaoImpl();
    @Override
    public void update(Long theatreId, String name, String location) {
         theatreDao.update(theatreId,name,location);
    }

    @Override
    public void delete(Long theatreId) {
        theatreDao.delete(theatreId);
    }

    @Override
    public Theatre findById(long theatreId) {
        return theatreDao.findById(theatreId);
    }

    @Override
    public List<Map<Movie, List<Theatre>>> getAllMoviesByTime(int hour) {
        return theatreDao.getAllMoviesByTime(hour);
    }
}
