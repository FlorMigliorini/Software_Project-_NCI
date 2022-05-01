package florence.migliorini.crossingborder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import florence.migliorini.model.CoordenationDTO;
import florence.migliorini.model.DirectionsMainDTO;
import florence.migliorini.model.Favorite;
import florence.migliorini.model.ResultsGeoDTO;
import florence.migliorini.model.RouteDTO;

public class HomeActivity extends AppCompatActivity {


    Button btnHide, dateButton, btnSearch, timeButton;
    private DatePickerDialog datePickerDialog;
    private ListView listView;
    private ArrayList<String> options;
    private LinearLayout listRoutes;
    private Integer favoriteSelection =0;
    private ImageView btnAddFav;
    private LinearLayout linearFilters;

    private List<RouteDTO> listRoutesTrain;
    private List<RouteDTO> listRoutesBus;
    private List<RouteDTO> listRoutesLuas;

    //Atributos de filtros
    private ConstraintLayout favoriteSelected = null;
    private ConstraintLayout filterActive;
    private ConstraintLayout iconFilterActive;
    private TextView textViewActive;
    private Integer typeTransportSelected = 0;
    private Spinner spinner;
    private RadioGroup radioGroup;
    RadioButton rbOneWay,rbReturn;
    AutoCompleteTextView autoCompleteTextViewPassegers;
    EditText etLocation, etDestination;

    FavoriteActivity favoriteActivity;
    int daySelected,monthSelected,yearSelected = 0;
    int hour, minute = 0;
    private String origin = null,destiny = null;

//    private Button button;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etLocation = findViewById(R.id.etLocation);
        etDestination = findViewById(R.id.etDestination);
        btnAddFav = findViewById(R.id.btn_Add_Fav);
        btnSearch = findViewById(R.id.btnSearch);
        //listView = findViewById(R.id.LV_list);
        listRoutes = findViewById(R.id.listRoutes);
        ScrollView scroll = findViewById(R.id.scrollViewMain);
        rbOneWay = findViewById(R.id.rbOneWay);
        rbReturn = findViewById(R.id.rbReturn);
        linearFilters = findViewById(R.id.linearFilters);
        radioGroup = findViewById(R.id.radio_group);
        //Inicializa as opções para núemro de passageiros
        options = new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.sp_passagers);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.passagers));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initDatePicker();
        dateButton = findViewById(R.id.buttonPickDate);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.btnSelectTime);
        btnHide = findViewById(R.id.btnHide);
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        yearSelected = LocalDate.now().getYear();
        daySelected = LocalDate.now().getDayOfMonth();
        monthSelected = LocalDate.now().getMonthValue();
    }

    //Preenche a lista de rotas
    /**
    * Ao iniciar uma busca por rotas esse método é invocado e
    * Ele recebe uma lista de possiveis rotas e preenche o layout linear
     * com as mesmas
    * **/
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fillListRoutes(List<RouteDTO> routes){
        /*Ao criar um elemento dinamicamente é necesario um id e nessa lista os ids
        são iniciados pela base 80 e são acrescentados de 1 em 1.*/
        int i = 80;
        for(RouteDTO route:routes){
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 50);
            //bloco principal
            ConstraintLayout objList = new ConstraintLayout(getApplicationContext());
            objList.setLayoutParams(layoutParams);
            objList.setBackgroundResource(R.drawable.shape_arredounded);
            objList.setMinHeight(230);
            //objList.setPadding(20,20,20,20);
            objList.setId(i);

            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT,MATCH_PARENT);
            /**
             * Cada rota tem seu layout baseado em 4 colunas
             * cada escopo abaixo corresponde a uma coluna com seus respectivos elementos
             * **/
            ConstraintLayout column1 = new ConstraintLayout(getApplicationContext());
            {
                column1.setMaxWidth(220);
                layoutParams.leftToLeft = objList.getId();
                layoutParams.topToTop = objList.getId();
                layoutParams.bottomToBottom = objList.getId();
                layoutParams.horizontalBias = (float) 0.0;
                layoutParams.verticalBias = (float) 0.0;
                column1.setId(i++);
                column1.setLayoutParams(layoutParams);
                column1.setPadding(40,10,0,0);
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
                locationText.setMaxWidth(220);
                //locationText.setTextSize(18);
                locationText.setTextColor(Color.rgb(15, 48, 99));
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
                timeText.setTextColor(Color.rgb(15, 48, 99));
                timeText.setLayoutParams(layoutParams);
                timeText.setText(route.getLegs().get(0).getDeparture_time().getText());
                column1.addView(timeText);
            }
            ConstraintLayout column2 = new ConstraintLayout(getApplicationContext());
            {
                layoutParams = new ConstraintLayout.LayoutParams(170,MATCH_PARENT);
                layoutParams.leftToRight = column1.getId();
                layoutParams.topToTop = objList.getId();
                layoutParams.bottomToBottom = objList.getId();
                layoutParams.horizontalBias = (float)0.009;
                layoutParams.verticalBias = (float) 0.0;
                column2.setId(i++);
                column2.setLayoutParams(layoutParams);
                column2.setPadding(20,10,0,0);
                TextView timeText = new TextView(getApplicationContext());
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                layoutParams.leftToLeft = column2.getId();
                layoutParams.topToTop = column2.getId();
                layoutParams.rightToRight = column2.getId();
                layoutParams.bottomToBottom = column2.getId();
                layoutParams.horizontalBias = (float) 0.481;
                layoutParams.verticalBias = (float) 0.285;
                layoutParams.topMargin = 40;
                timeText.setId(i++);
                timeText.setTextSize(14);
                timeText.setTextColor(Color.rgb(15, 48, 99));
                timeText.setLayoutParams(layoutParams);
                timeText.setText(route.getLegs().get(0).getDuration().getText()
                        .replaceAll("\\shour[s]+\\s",".")
                        .replaceAll("\\smin[s]+(\\s)?","m"));
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
                column3.setMaxWidth(220);
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT,MATCH_PARENT);
                layoutParams.leftToRight = column2.getId();
                layoutParams.topToTop = objList.getId();
                layoutParams.bottomToBottom = objList.getId();
                layoutParams.horizontalBias = (float) 0.601;
                layoutParams.verticalBias = (float) 0.0;
                column3.setId(i++);
                column3.setLayoutParams(layoutParams);
                column3.setPadding(20,10,0,0);
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
                destineText.setMaxWidth(220);
                //destineText.setTextSize(18);
                destineText.setTextColor(Color.rgb(15, 48, 99));
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
                timeText.setTextColor(Color.rgb(15, 48, 99));
                timeText.setLayoutParams(layoutParams);
                timeText.setText(route.getLegs().get(0).getArrival_time().getText());
                column3.addView(timeText);
            }
            ConstraintLayout column4 = new ConstraintLayout(getApplicationContext());
            {
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT,MATCH_PARENT);
                layoutParams.leftToRight = column3.getId();
                layoutParams.topToTop = objList.getId();
                layoutParams.bottomToBottom = objList.getId();
                layoutParams.rightToRight = objList.getRight();
                layoutParams.horizontalBias = (float) 1;
                layoutParams.verticalBias = (float) 0.0;
                column4.setId(i++);
                column4.setLayoutParams(layoutParams);
                column4.setPadding(20,0,0,0);

                TextView titleText = new TextView(getApplicationContext());
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                layoutParams.leftToLeft = column4.getId();
                layoutParams.topToTop = column4.getId();
                layoutParams.rightToRight = column4.getId();
                layoutParams.bottomToBottom = column4.getId();
                //layoutParams.bottomMargin = 10;
                layoutParams.verticalBias = (float) 0.107;
                titleText.setId(i++);
                titleText.setTextSize(10);
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
                priceText.setTextColor(Color.rgb(15, 48, 99));
                priceText.setLayoutParams(layoutParams);
                String price;
                Integer priceInt = 100;
                String numberPassangers="1";
                //Calculo do valor baseado em 0.10 por KM
                if(route.getLegs().get(0).getDistance()!=null){
                    price = route.getLegs().get(0).getDistance().getText().split(" ")[0];
                    DecimalFormat formatPrice = new DecimalFormat("#.00");
                    numberPassangers = spinner.getSelectedItem().toString();
                    numberPassangers = numberPassangers.split(" ")[0];
                    price = String.valueOf(formatPrice.format(0.20*Double.parseDouble(price)));
                    RadioButton rbOneWay = findViewById(R.id.rbOneWay);
                    if(!rbOneWay.isChecked()){
                        price = String.valueOf(Double.parseDouble(price)*2);
                    }
                    price = String.valueOf(Double.parseDouble(price)*Integer.parseInt(numberPassangers));
                    priceInt = Integer.parseInt(String.valueOf(price)
                            .replaceAll("\\.",""));
                    priceText.setText("€ "+Double.parseDouble(price));
                }
                column4.addView(priceText);

                ConstraintLayout btnGetTicket = new ConstraintLayout(getApplicationContext());
                layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                layoutParams.leftToLeft = column4.getId();
                layoutParams.topToBottom = priceText.getId();
                layoutParams.rightToRight = column4.getId();
                layoutParams.bottomToBottom = column4.getId();
                btnGetTicket.setBackgroundResource(R.drawable.shape_arredounded_fifteen_blue);
                btnGetTicket.setPadding(20,20,20,20);
                btnGetTicket.setLayoutParams(layoutParams);
                Integer finalPriceInt = priceInt;
                String finalNumberPassangers = numberPassangers;
                btnGetTicket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, PaymentActivity.class);
                        intent.putExtra("titleTicket",route.getLegs().get(0).getArrival_time().getTime_zone());
                        intent.putExtra("locationName",route.getLegs().get(0).getStart_address().split(",")[0]);
                        intent.putExtra("locationTime",route.getLegs().get(0).getDeparture_time().getText());
                        intent.putExtra("destinationName",route.getLegs().get(0).getEnd_address().split(",")[0]);
                        intent.putExtra("destinationTime",route.getLegs().get(0).getArrival_time().getText());
                        intent.putExtra("TimeTicket",route.getLegs().get(0).getDuration().getText()
                                .replaceAll("\\shour[s]+\\s",".")
                                .replaceAll("\\smin[s]+","m"));
                        intent.putExtra("numberPersons", finalNumberPassangers);
                        intent.putExtra("imgTypeTransport",typeTransportSelected);
                        intent.putExtra("favoriteSelection",favoriteSelection);
                        //PRICE NÃO ESTÁ FORMATADO EM CASO DO FARE VIR
                        String finalPrice = String.valueOf(finalPriceInt);
                        if(String.valueOf(finalPriceInt).length()==2){
                            finalPrice+="0";
                        }else if(String.valueOf(finalPriceInt).length()==1){
                            finalPrice+="00";
                        }
                        intent.putExtra("value", finalPrice);
                        String value = String.valueOf(finalPriceInt);
                        intent.putExtra("ticketPrice","EUR "+ value);
                        startActivity(intent);
                        finish();
                    }
                });
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
                btnGetTicket.addView(iconRight);
                column4.addView(btnGetTicket);

            }
            //Adição das colunas no bloco principal.
            objList.addView(column1);
            objList.addView(column2);
            objList.addView(column3);
            objList.addView(column4);
            //Adição do bloco principal no layout linear
            listRoutes.addView(objList);
        }
        listRoutes.invalidate();
    }

    //Consulta de rotas
    /**
     * Ação de consultar possiveis rotas.
     * O método executa 3 requisições na api Directions do google
     * https://developers.google.com/maps/documentation/directions/overview
     * 1. Busca rotas para o tipo train
     * 2. Busca rotas para o tipo bus
     * 3. Busca rotas para o tipo tram
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
    public void searchAction(View view){
        //Pede as coordenadas de um endereço especifico.
        getCoordenation(etDestination.getText().toString(),2);
        getCoordenation(etLocation.getText().toString(),1);
        new Timer().schedule(new TimerTask() {
                //O timer é usado aplicando o delay de 500 porque ao solicitas as coordenadas acima
                //A requisição não vira imediatamente e os 500 foram suficientes para a resposta a principio.
                    @Override
                    public void run() {
                        //Montagem do URL BASE para todas as demais requisições.

                        String dateFormat="";
                        //Chave da api
                        String API_KEY = "AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
                        String urlBase = "https://maps.googleapis.com/maps/api/directions/json?";
                        urlBase+="key="+API_KEY;
                        urlBase+="&alternatives=true";
                        Calendar calendar = Calendar.getInstance();
                        if(daySelected!=0){
                            calendar.set(yearSelected,monthSelected,daySelected);
                        }
                        if(hour!=0){
                            calendar.set(yearSelected,monthSelected,daySelected,hour,minute);
                        }
                        dateFormat = String.valueOf(calendar.getTimeInMillis()/1000);
                        if(dateFormat!=""){
                            urlBase+="&departure_time="+dateFormat;
                        }
                        urlBase+="&destination="+destiny;
                        urlBase+="&origin="+origin;
                        urlBase+="&mode=transit";
                        //urlBase+="&transit_mode=tram|train|bus";
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        try {
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
                                            typeTransportSelected = 1;
                                            listRoutesTrain = modelMaps.getRoutes();
                                            //fillListRoutes(modelMaps.getRoutes());
                                            linearFilters.setVisibility(View.VISIBLE);
                                            filterActive = findViewById(R.id.blockBtnTrainTypeHome);
                                            iconFilterActive = findViewById(R.id.btnTrainTypeHome);
                                            textViewActive = findViewById(R.id.txBtnTrainTypeHome);
                                            listRoutesTrain = modelMaps.getRoutes();
                                            filterTrain(findViewById(R.id.blockBtnTrainTypeHome));
                                        }
                                    }else {
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            JsonObjectRequest jsonRequestBus=new JsonObjectRequest(Request.Method.GET,
                                    urlBase+"&transit_mode=bus",
                                    null, new Response.Listener<JSONObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(response!=null) {
                                        Gson gson=new Gson();
                                        DirectionsMainDTO modelMaps = gson.fromJson(response.toString(),DirectionsMainDTO.class);
                                        if(modelMaps.getRoutes().size()>0){
                                            //fillListRoutes(modelMaps.getRoutes());
                                            listRoutesBus = modelMaps.getRoutes();
                                            if(typeTransportSelected.equals(0)){
                                                typeTransportSelected = 2;
                                                filterActive = findViewById(R.id.blockBtnBusTypeHome);
                                                iconFilterActive = findViewById(R.id.btnBusTypeHome);
                                                textViewActive = findViewById(R.id.txBtnBusTypeHome);
                                                filterBus(findViewById(R.id.blockBtnBusTypeHome));
                                            }
                                            linearFilters.setVisibility(View.VISIBLE);
                                        }
                                    }else {
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            JsonObjectRequest jsonRequestLuas=new JsonObjectRequest(Request.Method.GET,
                                    urlBase+"&transit_mode=tram",
                                    null, new Response.Listener<JSONObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(response!=null) {
                                        Gson gson=new Gson();
                                        DirectionsMainDTO modelMaps = gson.fromJson(response.toString(),DirectionsMainDTO.class);
                                        if(modelMaps.getRoutes().size()>0){
                                            //fillListRoutes(modelMaps.getRoutes());
                                            listRoutesLuas = modelMaps.getRoutes();
                                            if(typeTransportSelected.equals(0)){
                                                typeTransportSelected = 3;
                                                filterActive = findViewById(R.id.blockBtnLuasTypeHome);
                                                iconFilterActive = findViewById(R.id.btnLuasTypeHome);
                                                textViewActive = findViewById(R.id.txBtnLuasTypeHome);
                                                filterLuas(findViewById(R.id.blockBtnLuasTypeHome));
                                            }
                                            linearFilters.setVisibility(View.VISIBLE);
                                        }
                                    }else {
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            requestQueue.add(jsonRequestTrain);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    requestQueue.add(jsonRequestBus);
                                }
                            }, 500L);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    requestQueue.add(jsonRequestLuas);
                                }
                            }, 1000L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
        }, 1000L);
    }
    //Método que alterna o icone do botão de adicionar aos favoritos.
    public void confirmAddFavorite(View view){
        if(favoriteSelection == 0){
            btnAddFav.setImageResource(R.drawable.ic_baseline_favorite_24);
            favoriteSelection = 1;
        }else{
            btnAddFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            favoriteSelection = 0;
        }
    }

    //Recebe a data de atual.
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
        //Inicializa o campo de localização.
        initialLocalization();
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
    //Inicializa a data atual nos atributos e inicia o evento de selecionar uma data.
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                monthSelected = month;
                yearSelected = year;
                daySelected = day;
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
    //Inicializa o evento de mudar o horario no botão.
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

    //
    /**
     * Os métodos a seguir ( filterTrain, filterBus, filterLuas eclearLastFilter)
     * Executam as ações de filtrar as rotas por um tipo de transporte especifico
     * e ação de limpar a lista de rotas antes de preencher a nova.
     * Aqui também a feita a lógica de trocar cores e icones de filtros relacionados
     * aos tipos de transporte.
     * **/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterTrain(View view){
        if(filterActive!=null){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.btn_madison);
            ConstraintLayout icon = findViewById(R.id.btnTrainTypeHome);
            TextView txBtn = findViewById(R.id.txBtnTrainTypeHome);
            icon.setBackgroundResource(R.drawable.ic_baseline_train_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            listRoutes.removeAllViews();
            typeTransportSelected = 1;
            try{
                fillListRoutes(listRoutesTrain);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterBus(View view){
        if(filterActive!=null){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.btn_madison);
            ConstraintLayout icon = findViewById(R.id.btnBusTypeHome);
            TextView txBtn = findViewById(R.id.txBtnBusTypeHome);
            icon.setBackgroundResource(R.drawable.ic_baseline_directions_bus_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            listRoutes.removeAllViews();
            typeTransportSelected = 2;
            try{
                fillListRoutes(listRoutesBus);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterLuas(View view){
        if(filterActive!=null){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.btn_madison);
            ConstraintLayout icon = findViewById(R.id.btnLuasTypeHome);
            TextView txBtn = findViewById(R.id.txBtnLuasTypeHome);
            icon.setBackgroundResource(R.drawable.ic_baseline_subway_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            listRoutes.removeAllViews();
            typeTransportSelected = 3;
            try{
                fillListRoutes(listRoutesLuas);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("ResourceType")
    public void clearLastFilter(){
        filterActive.setBackgroundResource(R.drawable.shape_arredounded);
        if(typeTransportSelected == 1){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_train_24);
        }else if(typeTransportSelected==2){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_directions_bus_24);
        }else if(typeTransportSelected==3){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_subway_24);
        }
        textViewActive.setTextColor(Color.rgb(56,56,56));
    }

    //Consulta coordenada de um endereço
    /**
     * O método a seguir consulta as coordenadas de um endereço enviado
     * utilizando a api geocoding do google
     * https://developers.google.com/maps/documentation/geocoding/overview
     *
     * A requisição para a api é feita também com volley
     * https://google.github.io/volley/
     *
     * Para manipulação do Json foi optado por usar o design DTO
     * porque fica mais transparente oque está sendo buscado e recebido ao invés de
     * ter que saber o nome de todas chaves em JSONObject.
     *
     * Para o casting de Json para DTO (Java class) foi usada a classe GSON
     * https://sites.google.com/site/gson/gson-user-guide
     * **/
    public synchronized CoordenationDTO getCoordenation(String address,int type){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        try {
            String urlBase = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    address.replaceAll("\\s","+")
                    .replaceAll("(R\\.)","Rua")+
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
                        if(model.getResults().size()>0){
                            double lat = model.getResults().get(0).getGeometry().getLocation().getLat();
                            double lng = model.getResults().get(0).getGeometry().getLocation().getLng();
                            if(type==1){
                                origin = lat+","+lng;
                            }else if(type==2){
                                destiny = lat+","+lng;
                            }
                        }
                    }else {
                        return;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonRequest);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Inicializa a localização atual
    /**
     * O método a seguir inicializa a localização atual com um raio não tão preciso.
     * Utilizando a api geolocation
     * https://developers.google.com/maps/documentation/geolocation/overview
     *
     * Aqui também é feita uma requisição para a api geocoding
     * https://developers.google.com/maps/documentation/geocoding/overview
     * para receber o nome da rua encontrado pela geolocation
     *
     * Para manipulação do Json foi optado por usar o design DTO
     * porque fica mais transparente oque está sendo buscado e recebido ao invés de
     * ter que saber o nome de todas chaves em JSONObject.
     *
     * Para o casting de Json para DTO (Java class) foi usada a classe GSON
     * https://sites.google.com/site/gson/gson-user-guide
     * **/
    public void initialLocalization(){
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
                            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                            try {
                                String urlBase = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                                        latitude +","+ longitude +
                                        "&key=AIzaSyDfQVjDNvyjLXEj-6AqMHUaCK6ZTc45EeE";
                                JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.GET,
                                        urlBase,
                                        null, new Response.Listener<JSONObject>() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @SuppressLint("ResourceAsColor")
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        if(response!=null) {
                                            try{
                                                String road =  response.getJSONArray("results").getJSONObject(0)
                                                        .getString("formatted_address").toString().split(",")[0].toString();
                                                etLocation.setText(road);
                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }
                                        }else {
                                            return;
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

}