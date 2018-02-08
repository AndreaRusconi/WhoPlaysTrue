package com.example.user.whoplays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by filip on 08/02/2018.
 */

public class NickNameAddActivity extends Activity implements View.OnClickListener{

    private Button nickNameDoneButton;
    private EditText nickNameEditText;
    private String email;
    private Toast toast;
    boolean flag = false;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name_add);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();




        nickNameDoneButton = findViewById(R.id.nick_name_button);
        nickNameEditText = findViewById(R.id.insert_nick_name_edit_text);

        nickNameDoneButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nick_name_button) {

            if (nickNameEditText.getText().toString() != null) {


                private void setCheckName(final FindPlayerActivity.MyCallback myCallback){
                    databaseReference.child("Giocatori").orderByChild("nickName").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            if (dataSnapshot.child("nickName").toString().equals(nickNameEditText.getText().toString())) {
                                Toast.makeText(NickNameAddActivity.this, "Nick-name gia in uso", Toast.LENGTH_SHORT).show();
                                flag = true;
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
                    myCallback.onCallback();
                }

                setCheckName(new FindPlayerActivity.MyCallback() {
                    @Override
                    public void onCallback() {


                        databaseReference.child("Giocatori").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {

                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                final String key = dataSnapshot.getKey();
                                databaseReference.child("Giocatori").child(key).child("nickName").setValue(nickNameEditText.getText().toString());
                                Intent intent = new Intent(getBaseContext(), WhoPlaysActivity.class);
                                startActivity(intent);
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

                    }

                });
            }
            else {
                Toast.makeText(this, "compila campo obbligatorio", Toast.LENGTH_SHORT).show();
            }

        }
    }


}

