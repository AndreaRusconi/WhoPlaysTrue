package com.example.user.whoplays;

import android.content.Intent;
import android.nfc.Tag;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
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
    ArrayList<String> arrayPlace = new ArrayList();
    ArrayList<String> arrayType = new ArrayList();
    ArrayList<String> arrayDate = new ArrayList();
    ArrayList<String> arrayNumberOfPlayer = new ArrayList();
    ArrayList<String> arrayUser = new ArrayList();
    ArrayList<String> arrayTime = new ArrayList();
    ArrayList<String> arrayKey = new ArrayList();
    ArrayList<HashMap<String,String>> data = new ArrayList<>();



    ListView listView;


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
        listView  = view.findViewById(R.id.listViewWhoPlays);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Partite").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String user = dataSnapshot.child("user").getValue().toString();
                String place = dataSnapshot.child("place").getValue().toString();
                String date = dataSnapshot.child("date").getValue().toString();
                String type = dataSnapshot.child("typeOfMatch").getValue().toString();
                String numberOfPlayer = dataSnapshot.child("numberOfPlayer").getValue().toString();
                String time = dataSnapshot.child("time").getValue().toString();
                String key = dataSnapshot.getKey();

                //creo una hasHmap ad ogni ciclo

                HashMap<String,String> map = new HashMap<>();

                //resource è il layout di come voglio ogni singolo item
                int resource = R.layout.listview_item_who_plays;

                //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                String[] from = {"user","place","date","numberOfPlayer"};

                //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                int[] to = {R.id.itemCreatorWhoPlaysTextView,R.id.itemPlaceWhoPlaysTextView,R.id.itemDateWhoPlaysTextView,R.id.itemTypeWhoPlaysTextView};


                SimpleAdapter adapter = new SimpleAdapter(getActivity(),data,resource,from,to);
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
                map.put("numberOfPlayer", "Cerco " + numberOfPlayer + " giocatori");

                //inserisco l hashMap nell arrayList
                data.add(map);
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
                intent.putExtra("place",arrayPlace.get(position));
                intent.putExtra("date",arrayDate.get(position));
                intent.putExtra("numberOfPlayer",arrayNumberOfPlayer.get(position));
                intent.putExtra("type",arrayType.get(position));
                intent.putExtra("user", arrayUser.get(position));
                intent.putExtra("time", arrayTime.get(position));
                intent.putExtra("key", arrayKey.get(position));
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
        // automatically handle clicks on the home/Up button, so long
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
