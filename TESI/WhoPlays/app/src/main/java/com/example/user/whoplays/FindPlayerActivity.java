package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by io on 20/01/2018.
 */

public class FindPlayerActivity extends AppCompatActivity{

    private TextView creatorTextView;
    private TextView typeTextView;
    private TextView placeTextView;
    private TextView dateTextView;
    private TextView timeTextView;

    String place;
    String date;
    String type;
    String number;
    String user;
    String time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setTitle("Distinta");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("TAG", "Eccomi");
            place = extras.getString("place");
            date = extras.getString("date");
            type = extras.getString("type");
            number = extras.getString("numberOfPlayer");
            user = extras.getString("user");
            time = extras.getString("time");
            //The key argument here must match that used in the other activity
        }

        creatorTextView = findViewById(R.id.creator_textView);
        typeTextView = findViewById(R.id.type_of_match_ads);
        placeTextView = findViewById(R.id.place_ads);
        timeTextView = findViewById(R.id.time_ads);
        dateTextView = findViewById(R.id.date_ads);

        creatorTextView.setText(user);
        typeTextView.setText(type);
        placeTextView.setText(place);
        timeTextView.setText(time);
        dateTextView.setText(date);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_ads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if(id == R.id.options_create_ads_menu){
            Toast toast= Toast.makeText(this,"ciao",Toast.LENGTH_SHORT);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }


}
