package com.example.egear;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.egear.R;
import com.example.egear.maps.directionhelpers.FetchURL;
import com.example.egear.maps.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

   private GoogleMap mMap;
   private LatLng userLocation;
   private final LatLng shopLocation = new LatLng(10.841380486, 106.8098008);
   private Button buttonCalculateDistance;
   private Polyline currentPolyline;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);

      Intent intent = getIntent();
      if (intent != null && intent.hasExtra("latitude") && intent.hasExtra("longitude")) {
         double latitude = intent.getDoubleExtra("latitude", 0);
         double longitude = intent.getDoubleExtra("longitude", 0);
         userLocation = new LatLng(latitude, longitude);
      }

      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);

      buttonCalculateDistance = findViewById(R.id.buttonCalculateDistance);
      buttonCalculateDistance.setOnClickListener(v -> calculateDistanceAndDrawRoute());
   }

   @Override
   public void onMapReady(@NonNull GoogleMap googleMap) {
      mMap = googleMap;

      if (userLocation != null) {
         mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
      }

      mMap.addMarker(new MarkerOptions().position(shopLocation).title("Shop Location"));
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 10));
   }

   private void calculateDistanceAndDrawRoute() {
      if (userLocation == null) {
         Toast.makeText(this, "User location not available", Toast.LENGTH_SHORT).show();
         return;
      }

      float[] results = new float[1];
      Location.distanceBetween(userLocation.latitude, userLocation.longitude,
              shopLocation.latitude, shopLocation.longitude, results);
      float distanceInMeters = results[0];
      float distanceInKm = distanceInMeters / 1000;
      int roundedDistance = Math.round(distanceInKm);

      Toast.makeText(this, "Distance: " + roundedDistance + " km", Toast.LENGTH_SHORT).show();

      String url = getUrl(userLocation, shopLocation, "driving");
      new FetchURL(MapActivity.this).execute(url, "driving");
   }

   private String getUrl(LatLng origin, LatLng dest, String directionMode) {
      String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
      String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
      String mode = "mode=" + directionMode;
      String parameters = str_origin + "&" + str_dest + "&" + mode;
      String output = "json";
      String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
      return url;
   }

   @Override
   public void onTaskDone(Object... values) {
      if (currentPolyline != null)
         currentPolyline.remove();
      currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
   }
}
