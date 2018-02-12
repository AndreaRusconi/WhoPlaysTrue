package com.example.user.whoplays;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 12/01/2018.
 */

public class SignUpActivity extends Activity {

    private EditText mNameField;
    private EditText mSurnameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mCreateAccountButton;
    private FirebaseUser user;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mNameField = findViewById(R.id.sign_up_name);
        mSurnameField = findViewById(R.id.sign_up_surname);
        mEmailField = findViewById(R.id.sign_up_email);
        mPasswordField = findViewById(R.id.sign_up_password);

        mCreateAccountButton = findViewById(R.id.button_create_account);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validateForm()) {
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                }
            }

        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            String name = mNameField.getText().toString();
                            String cognome = mSurnameField.getText().toString();
                            if (user != null) {
                                Log.d("TAG", name);
                                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name + " " + cognome)
                                        .build();

                                user.updateProfile(profile)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }

                                        });

                            }

                            writeNodeUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]

        final String name = mNameField.getText().toString();

    }


    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else if (password.length() < 6) {
            mPasswordField.setError("Password too short");
        } else {
            mPasswordField.setError(null);
        }

        String name = mNameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mNameField.setError("Required.");
            valid = false;
        } else {
            mNameField.setError(null);
        }

        String surname = mSurnameField.getText().toString();
        if (TextUtils.isEmpty(surname)) {
            mSurnameField.setError("Required.");
            valid = false;
        } else {
            mSurnameField.setError(null);
        }


        return valid;
    }

    public void writeNodeUser() {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://my-project-1498298521137.firebaseio.com/Giocatori");

        String id = mAuth.getCurrentUser().getUid();
        String name = mNameField.getText().toString();
        String cognome = mSurnameField.getText().toString();
        String email = mEmailField.getText().toString();

        Player player = new Player(id, name + " " + cognome, email);
        databaseReference.child(id).setValue(player, new DatabaseReference.CompletionListener() {
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