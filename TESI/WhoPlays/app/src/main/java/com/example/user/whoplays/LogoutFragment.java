package com.example.user.whoplays;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.whoplays.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by io on 28/12/2017.
 */

public class LogoutFragment extends Fragment {

        Button button;
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public void onStart() {
       super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            getActivity().setTitle(R.string.logOut);
            mAuth = FirebaseAuth.getInstance();
            return inflater.inflate(R.layout.fragment_logout,null);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            button = (Button) view.findViewById(R.id.logout);

            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
            };


            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                   mAuth.signOut();
                }
            });
        }


}

