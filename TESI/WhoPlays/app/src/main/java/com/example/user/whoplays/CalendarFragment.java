package com.example.user.whoplays;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CalendarFragment extends Fragment {
    DatabaseReference databaseReference;
    ArrayList<String> arrayKey = new ArrayList<>();
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    ListView listView;
    private String emailUser;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        listView = view.findViewById(R.id.listViewCalendar);
        emailUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Log.d("TEG", "ECCOMI");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Partite").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot issue : dataSnapshot.child("partecipanti").getChildren()) {

                    if (issue.getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
                        Log.d("NEWTAG", issue.getValue().toString());
                        String user = dataSnapshot.child("user").getValue().toString();
                        String place = dataSnapshot.child("place").getValue().toString();
                        String date = dataSnapshot.child("date").getValue().toString();
                        String type = dataSnapshot.child("typeOfMatch").getValue().toString();
                        String numberOfPlayer = dataSnapshot.child("numberOfPlayer").getValue().toString();
                        String time = dataSnapshot.child("time").getValue().toString();
                        String key = dataSnapshot.getKey();
                        String address = dataSnapshot.child("latLng").getValue().toString();

                        HashMap<String, String> map = new HashMap<>();

                        //resource è il layout di come voglio ogni singolo item
                        int resource = R.layout.listview_item_calendar;

                        //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                        String[] from = {"place", "date", "time", "type"};

                        //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                        int[] to = {R.id.itemPlaceCalendarTextView, R.id.itemDateCalendarTextView, R.id.itemTimeCalendarTextView, R.id.itemTypeCalendarTextView};
                        
                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to);
                        listView.setAdapter(adapter);

                        arrayKey.add(key);

                        //inserisco i dati nell HashMAp

                        map.put("time", time +", ");
                        map.put("date", date + ", ");
                        map.put("place", place);
                        map.put("type", type);
                        //inserisco l hashMap nell arrayList
                        data.add(map);
                    }

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(getContext(), FindPlayerActivity.class);
                intent.putExtra("key", arrayKey.get(position));
                startActivity(intent);
            }
        });

        return view;
    }


}