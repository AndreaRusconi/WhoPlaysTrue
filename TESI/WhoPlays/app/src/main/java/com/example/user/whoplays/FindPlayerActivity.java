package com.example.user.whoplays;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private Button  letMeSee;
    private ListView listView;
    ArrayList<HashMap<String,String>> data = new ArrayList<>();
    ArrayList<String> arrayIdPartita = new ArrayList<>();

    private Button addMeButton;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    String place;
    String date;
    String type;
    String number;
    String user;
    String time;
    String key;
    Boolean registered = false;
    Integer playerType;
    String keyG ;


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
        letMeSee = findViewById(R.id.let_me_see_textView);




        creatorTextView = findViewById(R.id.creator_textView);
        typeTextView = findViewById(R.id.type_of_match_ads);
        placeTextView = findViewById(R.id.place_ads);
        timeTextView = findViewById(R.id.time_ads);
        dateTextView = findViewById(R.id.date_ads);
        numberTextView = findViewById(R.id.number_of_player_ads);
        listView = findViewById(R.id.listViewFindPlayer);


        creatorTextView.setText(user);
        typeTextView.setText(type);
        placeTextView.setText(place);
        timeTextView.setText(time);
        dateTextView.setText(date);
        numberTextView.setText(number);


        FirebaseUser userFireBase = FirebaseAuth.getInstance().getCurrentUser();
        String userG = userFireBase.getDisplayName();
        final String emailG = userFireBase.getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        setCheckId(new MyCallback() {
            @Override
            public void onCallback() {

                setButton();
            }
        });


        addMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (playerType) {
                    case 0:
                        new AlertDialog.Builder(FindPlayerActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Cancella annuncio")
                                .setMessage("Sei sicuro di voler eliminare l'annuncio?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //***************************************



                                        databaseReference.child("Giocatori").addChildEventListener(new ChildEventListener() {


                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                String kG = dataSnapshot.child("playerId").getValue().toString();
                                                
                                                    for (DataSnapshot issue : dataSnapshot.child("idPartita").getChildren()) {
                                                        // do with your result

                                                        Log.d("TEG", kG + "     " + issue.getValue().toString());

                                                        if (issue.getValue().toString().equals(key)){
                                                               DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
                                                               databaseReference2.child("Giocatori").child(kG).child("idPartita").child(issue.getValue().toString()).removeValue();
                                                            Log.d("TEG", "yeeeeeeeeeeeeeeeeeeeeee");
                                                        }


                                                    }




                                            }


                                            @Override
                                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                                            }

                                            @Override
                                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                                      //  Log.d("TAG POLO", arrayIdPartita.get(0));

















                                        //***************************************************************************************

                                        databaseReference.child("Partite").child(key).setValue(null);
                                        Toast.makeText(getBaseContext(), "Partita eliminata", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getBaseContext(), WhoPlaysActivity.class));

                                    }

                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;
                    case 1:
                        databaseReference.child("Partite").child(key).child("partecipanti").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).setValue(null);
                        Integer addNumber = Integer.parseInt(number) + 1;
                        databaseReference.child("Partite").child(key).child("numberOfPlayer").setValue(addNumber);

                        //******************************************************************************

                        Query query = databaseReference.child("Giocatori").orderByChild("email").equalTo(emailG);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    keyG = null;
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                        // do with your result
                                        keyG = issue.child("playerId").getValue().toString();
                                    }

                                    Log.d("TAG KEY", keyG);

                                    databaseReference.child("Giocatori").child(keyG).child("idPartita").child(key).setValue(null, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError != null) {
                                                Toast.makeText(getBaseContext(), "Data could not be saved. " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    databaseReference.child("Partite").child(key).child("partecipanti").child(keyG).setValue(null);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });






                         //************************************************************************
                        Toast.makeText(getBaseContext(), "Hai annullato la partecipazione", Toast.LENGTH_SHORT).show();
                        stopAlarm();
                        startActivity(new Intent(getBaseContext(), WhoPlaysActivity.class));
                        break;


                    case 2:
                        Integer subNumber = Integer.parseInt(number) - 1;
                        databaseReference.child("Partite").child(key).child("numberOfPlayer").setValue(subNumber);
                        //*****************************************************************

                        Query query1 = databaseReference.child("Giocatori").orderByChild("email").equalTo(emailG);
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String keyG = null;
                                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                        // do with your result
                                        keyG = issue.child("playerId").getValue().toString();
                                    }

                                    Log.d("TAG KEY", keyG);

                                    databaseReference.child("Partite").child(key).child("partecipanti").child(keyG).setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                                    databaseReference.child("Giocatori").child(keyG).child("idPartita").child(key).setValue(key, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError != null) {
                                                Toast.makeText(getBaseContext(), "Data could not be saved. " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                        //*********************************************************
                        Toast.makeText(getBaseContext(), "Ti sei aggiunto alla partita", Toast.LENGTH_SHORT).show();
                        startAlarm();
                        startActivity(new Intent(getBaseContext(), WhoPlaysActivity.class));
                        break;
                    case 3:
                        Toast.makeText(getBaseContext(), "La partita é giá completa", Toast.LENGTH_SHORT).show();
                        break;
                }

            }


        });


        letMeSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" +place);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

    }

    private void setCheckId(final MyCallback myCallback){
        databaseReference.child("Partite").child(key).child("partecipanti").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI



                Log.d("TAG", "log zero");
                if (dataSnapshot.exists()) {



                    Log.d("TAG", "primo log");
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        String giocatore = issue.getValue().toString();
                        //creo una hasHmap ad ogni ciclo
                        HashMap<String, String> map = new HashMap<>();
                        //resource è il layout di come voglio ogni singolo item
                        int resource = R.layout.listview_item_find_player;
                        //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                        String[] from = {"nome_giocatore", "numero_partite "};
                        //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                        int[] to = {R.id.nome_giocatore_item_find_player};

                        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, resource, from, to);
                        listView.setAdapter(adapter);

                        // do something with the individual "issues"
                        Log.d("TAG",giocatore);

                        map.put("nome_giocatore", giocatore);

                        data.add(map);

                        // do something with the individual "issues"
                        Log.d("TAG", issue.getValue().toString());
                        if (issue.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                            Log.d("TAG", issue.getValue().toString());
                            registered = true;
                        }
                    }
                }
                // ...
                myCallback.onCallback();
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

        if (user.equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
            addMeButton.setText("Cancella partita");
            playerType = 0;
        } else if (registered) {
            addMeButton.setText("Togli partecipazione");
            playerType = 1;
        }
        else {
            if (Integer.parseInt(number) > 0) {
                addMeButton.setText("Aggiungimi alla partita");
                playerType = 2;
            }
            else {
                addMeButton.setText("Partita completa");
                playerType = 3;
            }
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


        return super.onOptionsItemSelected(item);
    }

    public interface MyCallback {
        void onCallback();
    }

    private void startAlarm() {

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        myIntent = new Intent(this, AlarmNotificationReceiver.class);
        String codice = date + time;
        Integer id = codice.hashCode();
        pendingIntent = PendingIntent.getBroadcast(this, id, myIntent, 0);




        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/M/yyyy h:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String inputDateStr = date;
        String inputTimeStr = time;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr + " " +inputTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        Long difference = date.getTime() - System.currentTimeMillis();
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + difference - 30*60*1000, pendingIntent);

    }



    private void stopAlarm() {

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        String codice = date + time;
        Integer id = codice.hashCode();

        Intent myIntent = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, id , myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);

    }


}
