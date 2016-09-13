package com.fathy.themovieapp;

import java.io.Serializable;

/**
 * Created by fathy
 */
public class MyMovie implements Serializable {
    String poster;
    String title;
    String releaseDate;
    int time;
    String overview;
    int id;
    double vote_average;



    public void set_ID_URL(int id, String poster){
        this.poster = poster;
        this.id = id;
    }
    public void setDetails( String title, String overview, String releaseDate, int time, double vote_average){
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.time = time;
        this.vote_average = vote_average;
    }

}
