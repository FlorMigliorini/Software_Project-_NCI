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

import org.json.JSONException;
import org.json.JSONObject;

import florence.migliorini.model.DirectionsMainDTO;
import florence.migliorini.model.ResultsGeoDTO;

public class RouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    private EditText addressText;

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

    //Executa ações inicia na Activity
    /**
     * O método a seguir pede permissão para usuario para utilizar algumas
     * ferramentas como por exemplo a localização em primeiro e segundo plano.
     *
     * o método também faz uma requisição para a api geolocation
     * https://developers.google.com/maps/documentation/geolocation/overview
     *
     * Para o consumo da api é utilizada a classe RequestQueue da biblioteca Volley
     * https://google.github.io/volley/
     *
     * Para manipulação do Json foi optado por usar o design DTO
     * porque fica mais transparente oque está sendo buscado e recebido ao invés de
     * ter que saber o nome de todas chaves em JSONObject.
     *
     * Para o casting de Json para DTO (Java class) foi usada a classe GSON
     * https://sites.google.com/site/gson/gson-user-guide
     * **/
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
                            Boolean backgroundLocation = result.getOrDefault(
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION, false);
                            Boolean networkLocation = result.getOrDefault(
                                    Manifest.permission.ACCESS_NETWORK_STATE, false);
                            Boolean internetLocation = result.getOrDefault(
                                    Manifest.permission.INTERNET, false);
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
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        try {
            String urlBase = "https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
            JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.POST,
                    urlBase,
                    null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(JSONObject response) {
                    if(response!=null) {
                        try {
                            double latitude = response.getJSONObject("location").getDouble("lat");
                            double longitude = response.getJSONObject("location").getDouble("lng");
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                                    longitude), 17.0f));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    //Inicia a instancia do atributo GoogleMap
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
    public void menuButton(View view) {
        Intent intent = new Intent(RouteActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    //Evento de alterar a localização do mapa
    public void searchAddress(View view){
        String address = addressText.getText().toString();
        alterAddressMap(address);
    }
    /**
     * O método a seguir faz uma busca para api geocoding e insere as coordenadas de retorno
     * no mapa.
     *
     * Para o consumo da api é utilizada a classe RequestQueue da biblioteca Volley
     * https://google.github.io/volley/
     *
     * Para manipulação do Json foi optado por usar o design DTO
     * porque fica mais transparente oque está sendo buscado e recebido ao invés de
     * ter que saber o nome de todas chaves em JSONObject.
     *
     * Para o casting de Json para DTO (Java class) foi usada a classe GSON
     * https://sites.google.com/site/gson/gson-user-guide
     * **/
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
                                longitude), 17.0f));
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