package com.example.user.whoplays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by io on 20/01/2018.
 */

public class FindPlayerActivity extends AppCompatActivity{

    private TextView creatorTextView;
    private TextView typeTextView;
    private TextView placeTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView numberTextView;

    private Button addMeButton;

    DatabaseReference databaseReference;

    String place;
    String date;
    String type;
    String number;
    String user;
    String time;
    String key;
    String checkId = "ciccio";
    Boolean registered = false;


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
            place = extras.getString("place");
            date = extras.getString("date");
            type = extras.getString("type");
            number = extras.getString("numberOfPlayer");
            user = extras.getString("user");
            time = extras.getString("time");
            key = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        addMeButton = findViewById(R.id.add_me_ads_button);

        creatorTextView = findViewById(R.id.creator_textView);
        typeTextView = findViewById(R.id.type_of_match_ads);
        placeTextView = findViewById(R.id.place_ads);
        timeTextView = findViewById(R.id.time_ads);
        dateTextView = findViewById(R.id.date_ads);
        numberTextView = findViewById(R.id.number_of_player_ads);

        creatorTextView.setText(user);
        typeTextView.setText(type);
        placeTextView.setText(place);
        timeTextView.setText(time);
        dateTextView.setText(date);
        numberTextView.setText(number);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-project-1498298521137.firebaseio.com/Partite");

        setCheckId(new MyCallback() {
            @Override
            public void onCallback(String value) {
                checkId = value;
                setButton();
            }
        });







        addMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer newNumber = Integer.parseInt(number) -1;
                databaseReference.child(key).child("numberOfPlayer").setValue(newNumber);
                databaseReference.child(key).child("playerAdded").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                startActivity(new Intent(getBaseContext(),WhoPlaysActivity.class));
            }
        });

    }

    private void setCheckId(final MyCallback myCallback){
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String value = dataSnapshot.child("playerId").getValue().toString();
                Log.d("TAG", "log zero");
                if (dataSnapshot.child("playerAdded").exists()) {
                    Log.d("TAG", "primo log");
                    for (DataSnapshot issue : dataSnapshot.child("playerAdded").getChildren()) {
                        // do something with the individual "issues"
                        Log.d("TAG", "secondo log");
                        if (issue.getValue().toString() == FirebaseAuth.getInstance().getCurrentUser().getUid())
                            registered = true;

                    }
                }
                // ...
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }


    private void setButton() {
        Log.d("TAG", checkId +", " +key);
        if (checkId == key) {
            addMeButton.setText("Cancella partita");
        } else if (registered) {
            addMeButton.setText("Togli partecipazione");
        }
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


    public interface MyCallback {
        void onCallback(String value);
    }


}
