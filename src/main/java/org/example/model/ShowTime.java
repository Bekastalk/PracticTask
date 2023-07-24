package org.example.model;

import java.time.LocalDateTime;

public class ShowTime {
    private long id;
    private long movieId;
    private long theatreId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ShowTime() {
    }

    public ShowTime(long movieId, long theatreId, LocalDateTime startTime, LocalDateTime endTime) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShowTime(long id, long movieId, long theatreId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(long theatreId) {
        this.theatreId = theatreId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ShowTime{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", theatreId=" + theatreId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
