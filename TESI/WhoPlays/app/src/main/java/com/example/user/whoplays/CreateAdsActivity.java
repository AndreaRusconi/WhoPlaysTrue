package com.example.user.whoplays;

/**
 * Created by io on 17/01/2018.
 */

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAdsActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView timeVIew;
    private int year, month, day, hour, minute;
    private Spinner spinnerTypeOfMatch;
    private TextView textViewCampo;
    private EditText numberOfPlayer;
    private Button confirmCreation;
    private String id;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);

       final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-project-1498298521137.firebaseio.com/Partite");

        confirmCreation = findViewById(R.id.confirm_ads_creation_button);
        user = FirebaseAuth.getInstance().getCurrentUser();


        confirmCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    id = databaseReference.push().getKey();
                    Player player = new Player(id, spinnerTypeOfMatch.getSelectedItem().toString(), dateView.getText().toString(), timeVIew.getText().toString(), textViewCampo.getText().toString(), Integer.parseInt(numberOfPlayer.getText().toString()), user.getDisplayName() );

                    databaseReference.child(id).setValue(player, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(getBaseContext(), "Data could not be saved. " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Data saved successfully.", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getBaseContext(),WhoPlaysActivity.class);
                                startActivity(intent);
                            }

                        }
                    });
                }

                }

        });

        numberOfPlayer = findViewById(R.id.number_of_player_editText);
        textViewCampo = (TextView) findViewById(R.id.set_place_button);
        textViewCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Start the SecondActivity
                    Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                    startActivityForResult(intent, 5);
                }


        });


        dateView = (TextView) findViewById(R.id.button1);
        timeVIew = (TextView) findViewById(R.id.button);
        calendar = Calendar.getInstance();


        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        spinnerTypeOfMatch = findViewById(R.id.type_of_match_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_match_type, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTypeOfMatch.setAdapter(adapter);
        numberOfPlayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(numberOfPlayer.getText())) {
                    if (Integer.parseInt(numberOfPlayer.getText().toString()) < 1 || Integer.parseInt(numberOfPlayer.getText().toString()) > 11) {
                        numberOfPlayer.setError("Numero di giocatori sbagliato.");
                    }
                }
            }
            });


    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    public void setTime(View view){
        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id == 998) {
            return new TimePickerDialog(this, myTimeListener, hour, minute, false);
        }

        return null;

    }

    private TimePickerDialog.OnTimeSetListener myTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    showTime(i, i1);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void showTime(int hour, int minute) {
        timeVIew.setText(new StringBuilder().append(hour).append(":")
        .append(minute));

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

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // set text view with string
                textViewCampo.setText(returnString);
            }
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String time = timeVIew.getText().toString();
        if (TextUtils.isEmpty(time)) {
            timeVIew.setError("Required.");
            valid = false;
        } else {
            timeVIew.setError(null);
        }

        String date = dateView.getText().toString();
        if (TextUtils.isEmpty(date)) {
            dateView.setError("Required.");
            valid = false;
        } else {
            dateView.setError(null);
        }

        String field = textViewCampo.getText().toString();
        if (TextUtils.isEmpty(field)) {
            textViewCampo.setError("Required.");
            valid = false;
        } else {
            textViewCampo.setError(null);
        }

        String number = numberOfPlayer.getText().toString();
        if (TextUtils.isEmpty(number)) {
            numberOfPlayer.setError("Required.");
            valid = false;
        } else {
            numberOfPlayer.setError(null);
        }

           return valid;
    }

}
