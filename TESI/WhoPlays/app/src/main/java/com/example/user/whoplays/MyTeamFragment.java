package com.example.user.whoplays;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by filip on 06/02/2018.
 */

public class MyTeamFragment extends Fragment {



    DatabaseReference databaseReference;
    ArrayList<String> arrayKey = new ArrayList<>();
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private TextView addNewTeam;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_team);
        View view = inflater.inflate(R.layout.fragment_my_team, null);


        addNewTeam = view.findViewById(R.id.add_new_team_my_team_text_view);
        addNewTeam.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){

                startActivity(new Intent(getContext(), CreateTeamActivity.class));
            }
        });


        getActivity().setTitle(R.string.my_team);
        listView = view.findViewById(R.id.list_view_my_team);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Squadre").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot issue : dataSnapshot.child("membri").getChildren()) {

                    if (issue.getValue().toString().equals(FirebaseAuth.getInstance().getUid())) {
                        Log.d("NEWTAG", issue.getValue().toString());
                        String founder = dataSnapshot.child("fondatore").getValue().toString();
                        String name = dataSnapshot.child("nome").getValue().toString();
                        String foundation = dataSnapshot.child("fondazione").getValue().toString();
                        String idTeam = dataSnapshot.child("idSquadra").getValue().toString();


                        HashMap<String, String> map = new HashMap<>();

                        //resource è il layout di come voglio ogni singolo item
                        int resource = R.layout.listview_item_my_team;

                        //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                        String[] from = {"founder", "name", "foundation"};

                        //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                        int[] to = {R.id.itemFounderMyTeamTextView, R.id.itemNameMyTeamTextView, R.id.itemDateMyTeamTextView};

                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to);
                        listView.setAdapter(adapter);

                        arrayKey.add(idTeam);

                        //inserisco i dati nell HashMAp

                        map.put("foundation", foundation + ", ");
                        map.put("founder", founder);
                        map.put("name", name);
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

                Intent intent = new Intent(getContext(), TeamViewActivity.class);
                intent.putExtra("key", arrayKey.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

}
