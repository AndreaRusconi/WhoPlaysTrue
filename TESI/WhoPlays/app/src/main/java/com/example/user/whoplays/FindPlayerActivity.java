package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by io on 20/01/2018.
 */

public class FindPlayerActivity extends Activity{

    private TextView creatorTextView;
    private TextView typeTextView;
    private TextView placeTextView;

    String place;
    String date;
    String type;
    String number;
    String user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Log.d("TAG", "Eccomi");
            place = extras.getString("place");
            date = extras.getString("date");
            type = extras.getString("type");
            number = extras.getString("numberOfPlayer");
            user = extras.getString("user");

            //The key argument here must match that used in the other activity
        }

        creatorTextView = findViewById(R.id.creator_textView);
        typeTextView = findViewById(R.id.type_of_match_ads);
        placeTextView = findViewById(R.id.place_ads);

        creatorTextView.setText(user);
        typeTextView.setText(type);
        placeTextView.setText(place);





    }
}
