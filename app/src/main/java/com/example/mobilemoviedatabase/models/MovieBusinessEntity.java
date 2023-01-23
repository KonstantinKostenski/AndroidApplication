package com.example.mobilemoviedatabase.models;

public class MovieBusinessEntity {
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getBusinessEntityId() {
        return businessEntityId;
    }

    public void setBusinessEntityId(int businessEntityId) {
        this.businessEntityId = businessEntityId;
    }

    private int movieId;
    private int businessEntityId;
}
