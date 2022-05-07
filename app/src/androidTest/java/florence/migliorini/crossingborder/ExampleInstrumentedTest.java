package florence.migliorini.crossingborder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import florence.migliorini.db.SQLiteMan;
import florence.migliorini.model.DirectionsMainDTO;
import florence.migliorini.model.StripeKeyDTO;
import florence.migliorini.model.TravelDTO;
import florence.migliorini.payment.ConfigStripe;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context context;

    @Before
    public void useAppContext() {
        // Context of the app under test
        context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
        assertEquals("florence.migliorini.crossingborder", context.getPackageName());
    }
    @Test
    public void timer(){
        Log.d("Junit Test","print");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("Junit Test","Teste");
            }
        },500L);
    }
    @Test
    public void getRoutes(){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        String API_KEY = "AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
        String urlBase = "https://maps.googleapis.com/maps/api/directions/json?";
        urlBase+="key="+API_KEY;
        urlBase+="&alternatives=true";
        urlBase+="&destination=Montreal";
        urlBase+="&origin=Toronto";
        urlBase+="&mode=transit";
        JsonObjectRequest jsonRequestTrain=new JsonObjectRequest(Request.Method.GET,
                urlBase+"&transit_mode=train",
                null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(JSONObject response) {
                if(response!=null) {
                    Gson gson=new Gson();
                    DirectionsMainDTO modelMaps = gson.fromJson(response.toString(),DirectionsMainDTO.class);
                    if(modelMaps.getRoutes().size()>0){
                        Log.d("Test Junit",modelMaps.toString());
                    }
                }else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Test Junit","Error");
            }
        });
        requestQueue.add(jsonRequestTrain);
    }
    @Test
    public void login(){
        SQLiteMan.getInstance(context,"databaseTeste").logoutUser();
        String emailUser = SQLiteMan.getInstance(context,"databaseTeste").getUserConnected();
        System.out.println(emailUser);
        SQLiteMan.getInstance(context,"databaseTeste")
                .setUserConnected("TestJunit@gmail.com");
        emailUser = SQLiteMan.getInstance(context,"databaseTeste").getUserConnected();
        System.out.println(emailUser);
    }
    @Test
    public void saveHistory(){
        TravelDTO travel = new TravelDTO(null,"Localizacao","Destino"
                , LocalDate.now(),1,"Duration",100,
                "Dt Duration","Titulo","Departure",
                "Tempo",10);
        SQLiteMan.getInstance(context,"databaseTeste")
                .addHistoric(travel);
    }
    @Test
    public void saveFavorite(){
        TravelDTO travel = new TravelDTO(null,"Localizacao","Destino"
                , LocalDate.now(),1,"Duration",100,
                "Dt Duration","Titulo","Departure",
                "Tempo",10);
        SQLiteMan.getInstance(context,"databaseTeste")
                .addFavorite(travel);
    }
    @Test
    public void createQrCodeWithZxing(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode("CodedExample", BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder brEncod = new BarcodeEncoder();
            Bitmap bit = brEncod.createBitmap(bitMatrix);
            Log.d("APP",bit.toString());
        }catch(WriterException e){
            assertTrue(false);
            e.printStackTrace();
        }
    }
    @Test
    public void configStripe(){
        ConfigStripe config = new ConfigStripe();
        Gson gson = new Gson();
        StripeKeyDTO strpe = gson.fromJson(config.getKey(Long.parseLong("100"))
                ,StripeKeyDTO.class);
        Log.d("Test Junit",strpe.getPaymentIntentClientSecret());
    }
}