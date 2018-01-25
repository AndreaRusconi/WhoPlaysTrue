package com.example.user.whoplays;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 01/12/2017.
 */



public class SettingsFragment extends Fragment{


    private Button modpass;
    private Button elimina;

    ProgressDialog dialog;

    private DatabaseReference databaseReference;
    private FirebaseUser user;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.settings);

        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        modpass = (Button) view.findViewById(R.id.button_modifica_password);
        elimina = (Button) view.findViewById(R.id.button_elimina_account);

        dialog = new ProgressDialog(getContext());

        auth= FirebaseAuth.getInstance();

        modpass.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                startActivity(new Intent(getContext(), ModPassActivity.class));
            }
        });

        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = auth.getCurrentUser();
                if(user!=null){
                    dialog.setMessage("Eliminazione in corso.. Attendere");
                    dialog.show();
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Account eliminato", Toast.LENGTH_SHORT).show();
                                
                                startActivity(new Intent(getContext(),LoginActivity.class));
                            }
                            else
                                Toast.makeText(getContext(),"qualcosa è andato storto",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        }



    });
        return view;
    }


}

