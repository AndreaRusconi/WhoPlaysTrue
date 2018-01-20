package com.example.user.whoplays;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by User on 01/12/2017.
 */

public class WhoPlaysFragment extends Fragment {


    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayPlace = new ArrayList();
    ArrayList<String> arrayDate = new ArrayList();
    ArrayList<String> arrayNumberOfPlayer = new ArrayList();
    ArrayList<String> arrayType = new ArrayList();
    ArrayList<String> arrayList = new ArrayList();
    ArrayList<String> arrayUser = new ArrayList();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_who_plays, container, false);
        getActivity().setTitle(R.string.app_name);
        listView = view.findViewById(R.id.listViewWhoPlays);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Partite").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String user = dataSnapshot.child("user").getValue().toString();
                String place = dataSnapshot.child("place").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String type = dataSnapshot.child("typeOfMatch").getValue().toString();
                String numberOfPlayer = dataSnapshot.child("numberOfPlayer").getValue().toString();
                arrayList.add(user + " cerca " +numberOfPlayer +" giocatori");
                adapter.notifyDataSetChanged();
                arrayPlace.add(place);
                arrayDate.add(date);
                arrayNumberOfPlayer.add(numberOfPlayer);
                arrayType.add(type);
                arrayUser.add(user);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Log.d("TAG",listView.getItemAtPosition(position).toString());
                Intent intent = new Intent(getContext(), FindPlayerActivity.class);
                intent.putExtra("place",arrayPlace.get(position));
                intent.putExtra("date",arrayDate.get(position));
                intent.putExtra("numberOfPlayer",arrayPlace.get(position));
                intent.putExtra("type",arrayType.get(position));
                intent.putExtra("user", arrayUser.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.who_plays, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_filter) {
            startActivity(new Intent(getActivity(), FilterActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
