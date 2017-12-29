package com.example.user.whoplays;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 15/12/2017.
 */

public class NewAdsActivity extends AppCompatDialogFragment implements View.OnClickListener {


    private Button teamAdsButton;
    private Button playerAdsButton;


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_new_ads, null);

        builder.setView(view)
                .setTitle(R.string.type_ads_new_event);

        teamAdsButton = view.findViewById(R.id.teamAdsButton);
        playerAdsButton = view.findViewById(R.id.playerAdsButton);

        teamAdsButton.setOnClickListener(this);
        playerAdsButton.setOnClickListener(this);
        return builder.create();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        Fragment fragment = null;


        switch (id) {
            case R.id.teamAdsButton:
                fragment = new CreateTeamAdsFragment();
                break;
            case R.id.playerAdsButton:
                fragment = new CreatePlayerAdsFragment();
                break;
            default:
                break;
        }

        dismiss();
        getFragmentManager().beginTransaction()
                .replace(R.id.screen_area, fragment)
                .commit();
    }

}
