package com.example.user.whoplays;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by User on 01/12/2017.
 */

public class WhoPlaysFragment extends Fragment {

    static final int REQUESR_LOCATION = 1;
    LocationManager locationManager;
    DatabaseReference databaseReference;
    ArrayList<String> arrayKey = new ArrayList<>();
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    ArrayList<String> arrayDelete = new ArrayList<>();
    String Ordine;
    String Tipo;
    Float Distance;
    Boolean flag;
    Float distanceInMeters;
    Double latitude;
    Double longitude;
    String nickName;


    ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        //  prendo gli argomenti passati dalla FilterActivity
        //  se sono vuoti assegno i valori di default


        Ordine = getArguments().getString("sort");
        Tipo = getArguments().getString("type");
        Distance = getArguments().getFloat("distance", 0);

        if (Ordine == null) {
            Ordine = "date";
        }
        if (Tipo == null) {
            Tipo = "Tutte le partite";
        }
        if (Distance == 0) {
            flag = true;
        } else {
            locationManager = (LocationManager)getActivity().getSystemService(getContext().LOCATION_SERVICE);
            getLocation();
        }

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_who_plays, container, false);
        getActivity().setTitle(R.string.app_name);
        listView = view.findViewById(R.id.listViewWhoPlays);

        // Leggiamo i dati senza il filtro sulla distanza

        if (flag) {

            //  connessione al database
            //  prendo i dati ordinati per data o numero giocatori

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

                    //  controllo scadenza della partita
                    if (!checkDeadline(date, time, key)) {

                        //  controllo il tipo di partita calcoio_a_cinque/calcio_a_sette/calcio_a_undici
                        if (Tipo.equals(type) || Tipo.equals("Tutte le partite")) {

                            // creo hashMap per ogni risultato
                            HashMap<String, String> map = new HashMap<>();

                            //resource è il layout di come voglio ogni singolo item
                            int resource = R.layout.listview_item_who_plays;

                            //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                            String[] from = {"user", "place", "date", "numberOfPlayer","time"};

                            //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                            int[] to = {R.id.itemCreatorWhoPlaysTextView, R.id.itemPlaceWhoPlaysTextView, R.id.itemDateWhoPlaysTextView, R.id.itemTypeWhoPlaysTextView,R.id.itemTimeWhoPlaysTextView};

                            if (getActivity() != null) {

                                SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to);
                                listView.setAdapter(adapter);

                                populateArray(date, place, user, numberOfPlayer, type, time, key);

                                //inserisco i dati nell HashMAp
                                populateMap(map, user, date, place, numberOfPlayer, type, time);
                            }
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
            // leggiamo i dati con il filtro sulla distanza
        } else {

            //connessione al database
            DatabaseReference mdatabaseReference1 = FirebaseDatabase.getInstance().getReference();
            mdatabaseReference1.child("Partite").orderByChild(Ordine).addChildEventListener(new ChildEventListener() {


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


                    if (Tipo.equals(type) || Tipo.equals("Tutte le partite")) {

                        HashMap<String, String> map = new HashMap<>();

                        //resource è il layout di come voglio ogni singolo item
                        int resource = R.layout.listview_item_who_plays;

                        //qui salvo una stringa con gli stessi nomi messi nell hashMAp
                        String[] from = {"user", "place", "date", "numberOfPlayer","time"};

                        //qui salvo un altro array contenenti l id di ogni widget del mio singolo item
                        int[] to = {R.id.itemCreatorWhoPlaysTextView, R.id.itemPlaceWhoPlaysTextView, R.id.itemDateWhoPlaysTextView, R.id.itemTypeWhoPlaysTextView, R.id.itemTimeWhoPlaysTextView};



                        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to);
                        listView.setAdapter(adapter);

                        try {

                            // calcolo la distanza selezionata nei filtri
                            Geocoder selected_place_geocoder = new Geocoder(getContext());
                            List<Address> address1;
                            address1 = selected_place_geocoder.getFromLocationName(address, 1);
                            if (address1 == null) {
                                //do nothing
                            } else {
                                Address location = address1.get(0);

                                double lat = location.getLatitude();
                                double lng = location.getLongitude();

                                Location targetLocation = new Location("");//provider name is unnecessary
                                targetLocation.setLatitude(lat);//your coords of course
                                targetLocation.setLongitude(lng);

                                Location yourPosition = new Location("");
                                yourPosition.setLatitude(latitude);
                                yourPosition.setLongitude(longitude);
                                distanceInMeters = yourPosition.distanceTo(targetLocation);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (distanceInMeters < (Distance * 1000)) {
                            populateArray(date, place, user, numberOfPlayer, type, time, key);
                            //inserisco i dati nell HashMAp
                            populateMap(map, user, date, place, numberOfPlayer, type,time);
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
        }

        // onCLickListener per ogni item della listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position,
                                    long id) {

                checkNickName(position, new FindPlayerActivity.MyCallback() {
                    @Override
                    public void onCallback() {
                        Intent intent = new Intent(getContext(), FindPlayerActivity.class);
                        intent.putExtra("key", arrayKey.get(position));
                        intent.putExtra("nickName", nickName);
                        startActivity(intent);
                    }

                });
            }

        });
        return view;
    }


    private void checkNickName(int position, final FindPlayerActivity.MyCallback myCallback1) {
        databaseReference.child("Giocatori").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nickName = dataSnapshot.child("nickName").getValue().toString();
                myCallback1.onCallback();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

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


    // salviamo i dati negli array
    public void populateArray(String date, String place, String user, String numberOfPlayer, String type, String time, String key) {
        arrayKey.add(key);
    }

    // salviamo i dati nell hashMap
    public void populateMap(HashMap<String, String> map, String user, String date, String place, String numberOfPlayer, String type, String time){

        map.put("user", user);
        map.put("date", date + ", ");
        map.put("place", place);
        map.put("time", time);
        if (Integer.parseInt(numberOfPlayer) > 0) {
            map.put("numberOfPlayer", "Cerco " + numberOfPlayer + " giocatori");
        } else {
            map.put("numberOfPlayer", "La partita é completa");
        }
        //inserisco l hashMap nell arrayList
        data.add(map);

    }

    //controlliamo che la data della partita sia una data futura( TRUE se passata, FALSE se futura)
    public boolean checkDeadline(String date, String time, String key) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/M/yyyy h:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String inputDateStr = date;
        String inputTimeStr = time;
        Date date1 = null;
        try {
            date1 = inputFormat.parse(inputDateStr + " " + inputTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((date1.getTime() - System.currentTimeMillis()) < 0) {
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
            databaseReference1.child("Partite").child(key).setValue(null);
            return true;
        }

        return false;
    }

    public void getLocation() {
        if (!(ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUESR_LOCATION);
            flag = true;
        }
        else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                flag = false;
            }
            else {
                flag = true;
                Toast.makeText(getActivity(), "Non é possibile usare la posizione, assicurarsi che sia attiva la geolocalizzazione", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUESR_LOCATION:
                getLocation();;
                break;
        }
    }
}