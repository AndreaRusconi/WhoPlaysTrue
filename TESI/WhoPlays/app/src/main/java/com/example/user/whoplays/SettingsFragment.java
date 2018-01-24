package com.example.user.whoplays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by User on 01/12/2017.
 */



public class SettingsFragment extends Fragment{

    private Button applica;
    private Button modpass;
    private Button elimina;
    private EditText email;

    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.settings);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        applica = (Button) view.findViewById(R.id.button_applica);
        modpass = (Button) view.findViewById(R.id.button_modifica_password);
        elimina = (Button) view.findViewById(R.id.button_elimina_account);
        email = (EditText) view.findViewById(R.id.edit_text_email);



        applica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taken =  email.getText().toString();
                if (!taken.isEmpty()) {
                    startActivity(new Intent(getContext(), WhoPlaysActivity.class));
                    Toast.makeText(getContext(), taken, Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getContext(), "nessuna mail inserita", Toast.LENGTH_LONG).show();
            }
        });

        modpass.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                startActivity(new Intent(getContext(), ModPassActivity.class));
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Cancella account")
                        .setMessage("Sei sicuro di voler eliminare il tuo account? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        return view;
    }


}

