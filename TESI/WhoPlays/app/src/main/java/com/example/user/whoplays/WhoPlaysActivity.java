package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;



public class WhoPlaysActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new WhoPlaysFragment())
                .commit();

    }
}
