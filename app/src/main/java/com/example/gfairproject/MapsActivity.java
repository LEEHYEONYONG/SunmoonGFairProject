package com.example.gfairproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    String strAddress;//주소(위도,경도로 바꿀때 사용.)
    String strRestaurantName;//음식점이름(마커)
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setTitle("안심식당지도");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        strAddress = intent.getStringExtra("Address");
        strRestaurantName = intent.getStringExtra("restaurantName");



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        List<Address> listAddress = null;

        //주소검색
        try{
            listAddress = geocoder.getFromLocationName(strAddress,10);
            System.out.println("주소확인: "+listAddress);
        }catch(IOException e){

        }

        //위도와 경도 저장
        String[] splitstr = listAddress.get(0).toString().split(",");
        //String latitude = splitstr[14].substring(splitstr[14].indexOf("=")+1);
        //String longitude = splitstr[16].substring(splitstr[16].indexOf("=")+1);
        Double latitude = listAddress.get(0).getLatitude();
        Double longitude = listAddress.get(0).getLongitude();
        System.out.println("위도: "+latitude);
        System.out.println("경도: "+longitude);


        // Add a marker in Sydney and move the camera
        LatLng restaurantPoint = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(restaurantPoint).title(strRestaurantName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantPoint,15));
    }

    //뒤로가기이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}