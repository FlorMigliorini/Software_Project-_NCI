package florence.migliorini.crossingborder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import florence.migliorini.model.Favorite;

public class HomeActivity extends AppCompatActivity {


    Button btnHide, dateButton, btnSearch, timeButton, btnAddFav;
    private DatePickerDialog datePickerDialog;
    private ListView listView;
    private ArrayList<String> options;

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
        listView = findViewById(R.id.LV_list);


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

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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