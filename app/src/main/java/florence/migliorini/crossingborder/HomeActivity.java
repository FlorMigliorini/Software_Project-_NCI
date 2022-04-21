package florence.migliorini.crossingborder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import florence.migliorini.db.DbHelper;
import florence.migliorini.model.DirectionsMainDTO;
import florence.migliorini.model.Favorite;
import florence.migliorini.model.RouteDTO;

public class HomeActivity extends AppCompatActivity {


    Button btnHide, dateButton, btnSearch, timeButton, btnAddFav;
    private DatePickerDialog datePickerDialog;
    private ListView listView;
    private ArrayList<String> options;
    private LinearLayout listRoutes;


    RadioButton radioButton;
    AutoCompleteTextView autoCompleteTextViewPassegers;
    EditText etLocation, etDestination;

    FavoriteActivity favoriteActivity;

    int hour, minute;

//    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etLocation = findViewById(R.id.etLocation);
        etDestination = findViewById(R.id.etDestination);
        btnAddFav = findViewById(R.id.btn_Add_Fav);
        btnSearch = findViewById(R.id.btnSearch);
        timeButton = findViewById(R.id.btnSelectTime);
        //listView = findViewById(R.id.LV_list);
        listRoutes = findViewById(R.id.listRoutes);

        radioButton = findViewById(R.id.rbOneWay);
        radioButton = findViewById(R.id.rbReturn);
        //autoCompleteTextViewPassegers = findViewById(R.id.autoCompleteTextViewPassegers);

        //display the list options
        options = new ArrayList<>();
        //how to display the option getting from the api?
//        ArrayAdapter<String> adapterTranspOption = new ArrayAdapter<~>(HomeActivity.this,
//                android.R.layout.simple_list_item_1, listView);
//        listView.setAdapter(adapterTranspOption);

        //passenger
        Spinner spinner = (Spinner) findViewById(R.id.sp_passagers);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.passagers));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

       saveFavorite();

        initDatePicker();
        dateButton = findViewById(R.id.buttonPickDate);
        dateButton.setText(getTodaysDate());
        btnHide = findViewById(R.id.btnHide);
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void searchAction(View view){
        String API_KEY = "AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
        String urlBase = "https://maps.googleapis.com/maps/api/directions/json?";
        urlBase+="key="+API_KEY;
        urlBase+="&destination="+etDestination.getText();
        urlBase+="&origin="+etLocation.getText();
        urlBase+="&mode=transit";
        urlBase+="&transit_mode=tram|train|bus";
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        try {
            JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET, urlBase,
                    null, new Response.Listener<JSONObject>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onResponse(JSONObject response) {
                    if(response!=null) {
                        Gson gson=new Gson();
                        DirectionsMainDTO modelMaps = gson.fromJson(response.toString(),DirectionsMainDTO.class);
                        int i = 80;
                        for(RouteDTO route:modelMaps.getRoutes()){
                            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                            //layoutParams.setMargins(0, 0, 0, 50);
                            //bloco principal
                            ConstraintLayout objList = new ConstraintLayout(getApplicationContext());
                            objList.setLayoutParams(layoutParams);
                            objList.setBackgroundResource(R.drawable.shape_arredounded);
                            objList.setMinHeight(200);
                            objList.setPadding(20,20,20,20);
                            //O LAYOUT está em formato de flex-box

                            layoutParams = new ConstraintLayout.LayoutParams(100,MATCH_PARENT);
                            ConstraintLayout column1 = new ConstraintLayout(getApplicationContext());
                            {
                                layoutParams.leftToLeft = objList.getId();
                                layoutParams.topToTop = objList.getId();
                                layoutParams.bottomToBottom = objList.getId();
                                layoutParams.horizontalBias = (float) 0.0;
                                layoutParams.verticalBias = (float) 0.0;
                                column1.setId(i);
                                column1.setLayoutParams(layoutParams);

                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column1.getId();
                                layoutParams.topToTop = column1.getId();
                                layoutParams.rightToRight = column1.getId();
                                layoutParams.bottomToBottom = column1.getId();
                                layoutParams.horizontalBias = (float) 0.481;
                                layoutParams.verticalBias = (float) 0.107;
                                TextView titleColumn = new TextView(getApplicationContext());
                                titleColumn.setText("Departure");
                                titleColumn.setTextSize(10);
                                titleColumn.setId(i++);
                                titleColumn.setLayoutParams(layoutParams);
                                column1.addView(titleColumn);

                                TextView locationText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column1.getId();
                                layoutParams.topToBottom = titleColumn.getId();
                                layoutParams.rightToRight = column1.getId();
                                layoutParams.bottomToBottom = column1.getId();
                                layoutParams.horizontalBias = (float) 0.481;
                                layoutParams.verticalBias = (float) 0.222;
                                locationText.setId(i++);
                                locationText.setTextSize(18);
                                locationText.setTextColor(R.color.madison);
                                locationText.setLayoutParams(layoutParams);
                                locationText.setText(route.getLegs().get(0).getStart_address().split(",")[0]);
                                column1.addView(locationText);

                                TextView timeText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column1.getId();
                                layoutParams.topToBottom = locationText.getId();
                                layoutParams.rightToRight = column1.getId();
                                layoutParams.bottomToBottom = column1.getId();
                                timeText.setId(i++);
                                timeText.setTextSize(12);
                                timeText.setTextColor(R.color.madison);
                                timeText.setLayoutParams(layoutParams);
                                timeText.setText(route.getLegs().get(0).getDeparture_time().getText());
                                column1.addView(timeText);
                            }
                            ConstraintLayout column2 = new ConstraintLayout(getApplicationContext());
                            {
                                layoutParams.leftToRight = column1.getId();
                                layoutParams.topToTop = objList.getId();
                                layoutParams.bottomToBottom = objList.getId();
                                layoutParams.horizontalBias = (float)0.009;
                                layoutParams.verticalBias = (float) 0.0;
                                column2.setId(i++);
                                column2.setLayoutParams(layoutParams);

                                TextView timeText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column2.getId();
                                layoutParams.topToTop = column2.getId();
                                layoutParams.rightToRight = column2.getId();
                                layoutParams.bottomToBottom = column2.getId();
                                layoutParams.horizontalBias = (float) 0.481;
                                layoutParams.verticalBias = (float) 0.285;
                                timeText.setId(i++);
                                timeText.setTextSize(14);
                                timeText.setTextColor(R.color.madison);
                                timeText.setLayoutParams(layoutParams);
                                timeText.setText(route.getLegs().get(0).getDuration().getText());
                                column2.addView(timeText);

                                ImageView icon = new ImageView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column2.getId();
                                layoutParams.topToBottom = timeText.getId();
                                layoutParams.rightToRight = column2.getId();
                                layoutParams.bottomToBottom = column2.getId();
                                icon.setLayoutParams(layoutParams);
                                icon.setImageResource(R.drawable.ic_baseline_arrow_right_alt_24);
                                column2.addView(icon);
                            }
                            ConstraintLayout column3 = new ConstraintLayout(getApplicationContext());
                            {
                                layoutParams.leftToRight = column2.getId();
                                layoutParams.topToTop = objList.getId();
                                layoutParams.bottomToBottom = objList.getId();
                                layoutParams.horizontalBias = (float) 0.601;
                                layoutParams.verticalBias = (float) 0.0;
                                column3.setId(i++);
                                column3.setLayoutParams(layoutParams);

                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column3.getId();
                                layoutParams.topToTop = column3.getId();
                                layoutParams.rightToRight = column3.getId();
                                layoutParams.bottomToBottom = column3.getId();
                                layoutParams.horizontalBias = (float) 0.481;
                                layoutParams.verticalBias = (float) 0.107;
                                TextView titleColumn = new TextView(getApplicationContext());
                                titleColumn.setText("Arrival");
                                titleColumn.setTextSize(10);
                                titleColumn.setId(i++);
                                titleColumn.setLayoutParams(layoutParams);
                                column3.addView(titleColumn);

                                TextView destineText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column3.getId();
                                layoutParams.topToBottom = titleColumn.getId();
                                layoutParams.rightToRight = column3.getId();
                                layoutParams.bottomToBottom = column3.getId();
                                layoutParams.horizontalBias = (float) 0.481;
                                layoutParams.verticalBias = (float) 0.222;
                                destineText.setId(i++);
                                destineText.setTextSize(18);
                                destineText.setTextColor(R.color.madison);
                                destineText.setLayoutParams(layoutParams);
                                destineText.setText(route.getLegs().get(0).getEnd_address().split(",")[0]);
                                column3.addView(destineText);

                                TextView timeText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column3.getId();
                                layoutParams.topToBottom = destineText.getId();
                                layoutParams.rightToRight = column3.getId();
                                layoutParams.bottomToBottom = column3.getId();
                                timeText.setId(i++);
                                timeText.setTextSize(12);
                                timeText.setTextColor(R.color.madison);
                                timeText.setLayoutParams(layoutParams);
                                timeText.setText(route.getLegs().get(0).getArrivalDTO().getText());
                                column3.addView(timeText);
                            }
                            ConstraintLayout column4 = new ConstraintLayout(getApplicationContext());
                            {
                                layoutParams = new ConstraintLayout.LayoutParams(120,MATCH_PARENT);
                                layoutParams.leftToRight = column4.getId();
                                layoutParams.topToTop = objList.getId();
                                layoutParams.bottomToBottom = objList.getId();
                                layoutParams.rightToRight = objList.getRight();
                                layoutParams.horizontalBias = (float) 0.601;
                                layoutParams.verticalBias = (float) 0.0;
                                column4.setId(i++);
                                column4.setLayoutParams(layoutParams);

                                TextView titleText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column4.getId();
                                layoutParams.topToTop = column4.getId();
                                layoutParams.rightToRight = column4.getId();
                                layoutParams.bottomToBottom = column4.getId();
                                layoutParams.verticalBias = (float) 0.125;
                                titleText.setId(i++);
                                titleText.setTextSize(14);
                                titleText.setLayoutParams(layoutParams);
                                titleText.setText("Price");
                                column4.addView(titleText);

                                TextView priceText = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = column4.getId();
                                layoutParams.topToBottom = titleText.getId();
                                layoutParams.rightToRight = column4.getId();
                                layoutParams.bottomToBottom = column4.getId();
                                layoutParams.verticalBias = (float) 0.125;
                                priceText.setId(i++);
                                priceText.setTextSize(18);
                                priceText.setTextColor(R.color.madison);
                                priceText.setLayoutParams(layoutParams);
                                priceText.setText("$ 10.00");
                                column4.addView(priceText);

                                ConstraintLayout btnGetTicket = new ConstraintLayout(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
                                layoutParams.leftToLeft = column4.getId();
                                layoutParams.topToBottom = priceText.getId();
                                layoutParams.rightToRight = column4.getId();
                                layoutParams.bottomToBottom = column4.getId();
                                btnGetTicket.setBackgroundResource(R.drawable.shape_arredounded_fifteen_blue);
                                TextView textGetTicket = new TextView(getApplicationContext());
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToLeft = btnGetTicket.getId();
                                layoutParams.topToTop = btnGetTicket.getId();
                                layoutParams.rightToRight = btnGetTicket.getId();
                                layoutParams.bottomToBottom = btnGetTicket.getId();
                                textGetTicket.setId(i++);
                                textGetTicket.setTextSize(18);
                                textGetTicket.setTextColor(Color.rgb(255, 250, 250));
                                textGetTicket.setLayoutParams(layoutParams);
                                textGetTicket.setText("Get ticket");
                                btnGetTicket.addView(textGetTicket);
                                ImageView iconRight = new ImageView(getApplicationContext());
                                iconRight.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                                layoutParams.leftToRight = textGetTicket.getId();
                                layoutParams.topToTop = btnGetTicket.getId();
                                layoutParams.rightToRight = btnGetTicket.getId();
                                layoutParams.bottomToBottom = btnGetTicket.getId();
                                iconRight.setLayoutParams(layoutParams);
                                column4.addView(btnGetTicket);

                            }
                            objList.addView(column1);
                            objList.addView(column2);
                            objList.addView(column3);
                            objList.addView(column4);
                            listRoutes.addView(objList);
                        }
                        listRoutes.invalidate();
                    }else {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonRequest);
            /*Gson gson=new Gson();
            JSONObject response = new JSONObject(getString(R.string.teste_json_maps));
            DirectionsMainDTO modelMaps = gson.fromJson(response.toString(),DirectionsMainDTO.class);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Api(EditText etLocation, EditText etDestination, Button search){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());;
        try {
            String url="https://api.nationaltransport.ie/gtfsr/v1?format=json";
            JSONObject json=new JSONObject();
            json.put("Location",etLocation.getText());
            json.put("Destination",etDestination.getText());
            JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(response!=null) {
                        Gson gson=new Gson();

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

    private void saveFavorite(){
         btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(HomeActivity.this);
                String strLocation = etLocation.getText().toString();
                String strDestination = etDestination.getText().toString();

                Favorite favorite = new Favorite(strLocation, strDestination);
                db.addFavorite(favorite);
                Intent i = new Intent(HomeActivity.this,FavoriteActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private String getTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month +1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
        startActivity(intent);
        finish();*/
    }

    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month){
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SET";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void checkButton(View view){

    }

    public void openTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //applying variables
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%2d:%02d", hour, minute));

            }
        };
        //applying a different style
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        return false;
//    }
}