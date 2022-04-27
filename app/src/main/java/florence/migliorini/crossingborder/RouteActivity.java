package florence.migliorini.crossingborder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.squareup.okhttp.Route;

import org.json.JSONObject;

import florence.migliorini.crossingborder.databinding.ActivityMapsBinding;
import florence.migliorini.model.DirectionsMainDTO;
import florence.migliorini.model.ResultsGeoDTO;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Button btn_search, btn_clear;
    private EditText addressText;
    Polyline currentPolyline;
    LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 5;
    private final long MIN_DIST = 5;
    private LatLng latLng;
    private ActivityMapsBinding binding;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_route);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        addressText = findViewById(R.id.TF_location);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(),
                                            location.getLongitude()))
                                    .title("map"));
                        } else {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(0,
                                            0))
                                    .title("map"));
                        }
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
    public void menuButton(View view) {
        Intent intent = new Intent(RouteActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void searchAddress(View view){
        String address = addressText.getText().toString();
        alterAddressMap(address);
    }
    public void alterAddressMap(String address){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        try {
            String urlBase = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    address.replaceAll("\\s","+") +
                    "&key=AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
            JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET,
                    urlBase,
                    null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(JSONObject response) {
                    if(response!=null) {
                        Gson gson=new Gson();
                        ResultsGeoDTO model = gson.fromJson(response.toString(), ResultsGeoDTO.class);
                        double latitude = model.getResults().get(0).getGeometry().getLocation().getLat();
                        double longitude = model.getResults().get(0).getGeometry().getLocation().getLng();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                                longitude), 12.0f));
                    }else {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clearMethod(View view){
        addressText.setText("");
    }



}