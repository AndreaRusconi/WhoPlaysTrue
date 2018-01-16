package com.example.user.whoplays;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 01/12/2017.
 */


public class MyTeamFragment extends Fragment {

    Firebase mRef;
    com.firebase.ui.FirebaseListAdapter<String> myAdapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.myTeam);

        return inflater.inflate(R.layout.fragment_my_team,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listViewMyTeam);


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mRef = new Firebase("https://<myURL>..");

            myAdapter = new FirebaseListAdapter<String>(this,String.class,android.R.layout.simple_list_item_1,mRef) {
                @Override
                protected void populateView(View view, String s, int i) {
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setText(s);
                }
            };
            final ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(myAdapter);

            Button addBtn = (Button) findViewById(R.id.add_button);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRef.push().setValue("test123");
                }
            });
        }
// Set the adapter of the ListView



    }

}