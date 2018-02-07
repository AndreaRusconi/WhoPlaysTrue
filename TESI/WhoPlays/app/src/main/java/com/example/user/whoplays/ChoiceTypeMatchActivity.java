package com.example.user.whoplays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by filip on 07/02/2018.
 */

public class ChoiceTypeMatchActivity extends Activity implements View.OnClickListener {



    private Button choiceTeamButton;
    private Button choicePlayerButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_type_match);


        choicePlayerButton = findViewById(R.id.button_player_choice_type_activity);
        choiceTeamButton = findViewById(R.id.button_team_choice_type_activity);

        choiceTeamButton.setOnClickListener(this);
        choicePlayerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_player_choice_type_activity) {

            startActivity(new Intent(getBaseContext(),CreateAdsActivity.class));

        }
        else if (v.getId() == R.id.button_team_choice_type_activity){
            startActivity(new Intent(getBaseContext(),CreateAdsActivity.class));

        }
    }
}
