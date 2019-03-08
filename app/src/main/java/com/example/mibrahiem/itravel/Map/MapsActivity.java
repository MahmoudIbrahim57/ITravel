package com.example.mibrahiem.itravel.Map;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mibrahiem.itravel.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btSearch;
    private EditText etDestination;
    String title;
    String destination;
    List<Address> address;
String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        country =   getIntent().getStringExtra("countryPosts");
        title =   getIntent().getStringExtra("title");
        btSearch=findViewById(R.id.bt_search);
         etDestination=findViewById(R.id.et_destination);


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
        LatLng sydney = null;
        // Add a marker in Sydney and move the camera
        switch (country){
            case "egypt" :        sydney= new LatLng(  31.205753, 29.924526);

            break;

            case "canada" :          sydney = new LatLng(56.130366, -106.34677099999999);

            break;

            case "germany" :          sydney = new LatLng(51.1657, 10.4515);

                break;

            case "emarat" :          sydney = new LatLng(25.276987, 55.296249);

            break;

        }
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination=etDestination.getText().toString();
                Geocoder geocoder=new Geocoder(MapsActivity.this);
                try {
                   address= geocoder.getFromLocationName(destination,1);

                    Address asd=address.get(0);
                    LatLng latLng =    new LatLng(asd.getLatitude(), asd.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(country));
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 500, null);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                } catch (IOException e) {
                 }

            }
        });
        mMap.addMarker(new MarkerOptions().position(sydney).title(country));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 100, null);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
