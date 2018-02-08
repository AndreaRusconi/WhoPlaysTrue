package com.example.user.whoplays;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    private Button confirmButton;
    Place place0 = null;
    String address;
    private Integer[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ids = new Integer[]{1, 1001, 1002, 1003, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                            17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 1005, 27, 28, 29, 30, 31, 32, 33,
                            35, 36, 37, 38, 39, 40, 41, 42, 1007, 43, 45, 46, 48, 49, 50, 51, 1008,
                            52, 53, 54, 55, 56, 1009, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 1010,
                            1011, 67, 68, 70, 71, 72, 73, 74, 75, 76, 1015, 1016, 1017, 1014,
                            77, 1018, 78, 79, 80, 1019, 1020, 82, 83, 84, 85, 87, 88, 1021, 1022, 89,
                            90, 1029, 91, 92, 93, 94, 95, 96};

        confirmButton = findViewById(R.id.confirm_place_button);
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                addMarker(place);
                Log.d("Maps", "Place selected: " + place.getAddress());
                place0 = place;
                address = (String) place.getAddress();

            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (place0 == null) {
                    Toast.makeText(getBaseContext(), "Seleziona un campo", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("keyName", place0.getName());
                    intent.putExtra("keyLatLng", address);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("TAG", "ahia");
            return;
        }
        else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            onMyLocationButtonClick();
        }

    }

    public void addMarker(Place p){

        boolean flag = true;
        p.getPlaceTypes();
        for (int i=0; i<p.getPlaceTypes().size(); i++){
            for (int j=0; j<ids.length; j++){
                if( p.getPlaceTypes().contains(ids[j]) ) {
                   Toast.makeText(this, "Posto selezionato non valido", Toast.LENGTH_SHORT).show();
                   flag = false;
                   break;
               }
            }
        }

        if (flag) {

            mMap.clear();
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(p.getLatLng());
            markerOptions.title(p.getName() + "");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(p.getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            Log.d("TAG", String.valueOf(p.getPlaceTypes()));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
}



