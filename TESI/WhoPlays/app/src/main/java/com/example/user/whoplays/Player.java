package com.example.user.whoplays;

/**
 * Created by io on 19/01/2018.
 */

public class Player {

    String playerId;
    String typeOfMatch;
    String date;
    String time;
    String place;
    Integer numberOfPlayer;

    public Player(){}

    public Player(String playerId, String typeOfMatch, String date, String time, String place, Integer numberOfPlayer){
        this.playerId = playerId;
        this.typeOfMatch = typeOfMatch;
        this.date = date;
        this.time = time;
        this.place = place;
        this.numberOfPlayer = numberOfPlayer;
    }

}
