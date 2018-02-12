package com.example.user.whoplays;

/**
 * Created by filip on 24/01/2018.
 */

public class Player {

    public String playerId;
    public String name;
    public String email;
    public String nickName;

    public Player(){}

    public Player(String playerId, String name, String email, String nickName){
        this.playerId = playerId;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
    }

}
