package com.example.movies_nicolay_nacaro.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="tickets_table")
public class Ticket {
    private String movieTitle;
    @PrimaryKey
    private int movieID;
    private int quantity;
public Ticket(String movieTitle,int movieID,int quantity){
    this.movieTitle = movieTitle;
    this.movieID = movieID;
    this.quantity = quantity;
}


    public int getMovieID() {
        return movieID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void addTick(){
    this.quantity++;
    }
}
