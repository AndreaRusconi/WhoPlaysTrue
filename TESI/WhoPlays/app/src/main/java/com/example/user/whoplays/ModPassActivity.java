package com.example.user.whoplays;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 24/01/2018.
 */

public class ModPassActivity extends Activity {
    EditText oldpass;
    EditText newpass;
    Button modifica;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_newpassword);

        oldpass = (EditText) findViewById(R.id.edit_text_oldpass);
        newpass = (EditText) findViewById(R.id.edit_text_newpass);
        modifica = (Button) findViewById(R.id.button_newpass);
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.button_newpass) {

                    if (oldpass.getText().toString().isEmpty() || newpass.getText().toString().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "campi vuoti", Toast.LENGTH_LONG).show();
                    }

                    else {
                        if (oldpass.getText().toString().equals(newpass.getText().toString())) {

                            Toast.makeText(getApplicationContext(), "la nuova password deve essere diversa", Toast.LENGTH_LONG).show();
                        } else {

                            user = mAuth.getCurrentUser();
                            email = user.getEmail();
                            AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass.getText().toString());

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {

                                                    Snackbar snackbar_fail = Snackbar
                                                            .make(getCurrentFocus(), "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
                                                    snackbar_fail.show();
                                                } else {


                                                    mAuth.signOut();
                                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                                                }
                                            }
                                        });
                                    } else {

                                        Snackbar snackbar_su = Snackbar
                                                .make(getCurrentFocus(), "Authentication Failed", Snackbar.LENGTH_LONG);
                                        snackbar_su.show();
                                    }
                                }
                            });
                        }
                    }
                }

            }
        });
    }
}
