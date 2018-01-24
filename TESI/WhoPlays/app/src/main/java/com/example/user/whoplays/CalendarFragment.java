package com.example.user.whoplays;

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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by User on 01/12/2017.
 */

public class CalendarFragment extends Fragment {
    DatabaseReference databaseReference;
    ArrayList<String> arrayPlace = new ArrayList();
    ArrayList<String> arrayType = new ArrayList();
    ArrayList<String> arrayDate = new ArrayList();
    ArrayList<String> arrayNumberOfPlayer = new ArrayList();
    ArrayList<String> arrayUser = new ArrayList();
    ArrayList<String> arrayTime = new ArrayList();
    ArrayList<String> arrayKey = new ArrayList();
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    ArrayList<String> arrayDelete = new ArrayList<>();



    ListView listView;


    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        listView = view.findViewById(R.id.listViewCalendar);

/*


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Partite").orderByChild(Ordine).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String user = dataSnapshot.child("user").getValue().toString();
                String place = dataSnapshot.child("place").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String type = dataSnapshot.child("typeOfMatch").getValue().toString();
                String numberOfPlayer = dataSnapshot.child("numberOfPlayer").getValue().toString();
                String time = dataSnapshot.child("time").getValue().toString();
                String key = dataSnapshot.getKey();
                String address = dataSnapshot.child("latLng").getValue().toString();

                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat formatter1 = new SimpleDateFormat("dd/M/yyyy h:mm");
                String currentDate = formatter1.format(calendar1.getTime());

                final String dateString = date;
                final String timeString = time;
                String datadb = dateString + " " + timeString;

//  Toast.makeText(context,"databse date:-"+datadb+"Current Date :-"+currentDate,Toast.LENGTH_LONG).show();

                if (currentDate.compareTo(datadb) <= 0) {
                    //creo una hasHmap ad ogni ciclo


                    if (Tipo.equals(type) || Tipo.equals("Tutte le partite")) {


                        HashMap<String, String> map = new HashMap<>();

                        //resource è il layout di come voglio ogni singolo item
                        int resource = R.layout.listview_item_who_plays;

                        //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                        String[] from = {"user", "place", "date", "numberOfPlayer"};

                        //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                        int[] to = {R.id.itemCreatorWhoPlaysTextView, R.id.itemPlaceWhoPlaysTextView, R.id.itemDateWhoPlaysTextView, R.id.itemTypeWhoPlaysTextView};


                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to);
                        listView.setAdapter(adapter);

                        arrayDate.add(date);
                        arrayPlace.add(place);
                        arrayUser.add(user);
                        arrayNumberOfPlayer.add(numberOfPlayer);
                        arrayType.add(type);
                        arrayTime.add(time);
                        arrayKey.add(key);

                        //inserisco i dati nell HashMAp

                        map.put("user", user);
                        map.put("date", date + ", ");
                        map.put("place", place + ", ");
                        if (Integer.parseInt(numberOfPlayer) > 0) {
                            map.put("numberOfPlayer", "Cerco " + numberOfPlayer + " giocatori" + " per " + type);
                        } else {
                            map.put("numberOfPlayer", "La partita é completa");
                        }
                        //inserisco l hashMap nell arrayList
                        data.add(map);
                    }

                } else {
                    Log.d("TAG", key);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
                    databaseReference1.child("Partite").child(key).setValue(null);

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
    }









*/


        return inflater.inflate(R.layout.fragment_calendar,container,false);
    }


}