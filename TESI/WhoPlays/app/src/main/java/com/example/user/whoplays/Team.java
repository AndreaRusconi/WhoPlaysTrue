package com.example.user.whoplays;

/**
 * Created by io on 19/01/2018.
 */

public class Team {

    public String idPartita;
    public String typeOfMatch;
    public String date;
    public String time;
    public String place;
    public Integer numberOfPlayer;
    public String user;
    public String latLng;

    public Team(){}

    public Team(String idPartita, String typeOfMatch, String date, String time, String place, Integer numberOfPlayer, String user, String latLng){
        this.idPartita = idPartita;
        this.typeOfMatch = typeOfMatch;
        this.date = date;
        this.time = time;
        this.place = place;
        this.numberOfPlayer = numberOfPlayer;
        this.user = user;
        this.latLng = latLng;
    }

}
