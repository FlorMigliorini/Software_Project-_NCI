package florence.migliorini.crossingborder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import florence.migliorini.db.SQLiteMan;
import florence.migliorini.model.Favorite;
import florence.migliorini.model.TravelDTO;

public class FavoriteActivity extends AppCompatActivity {

    private Button btnDelete,btnBack;
    private List<TravelDTO> listFavorites;
    private LinearLayout lnListFavorites;

    //Atributos de elementos selecionado
    private ConstraintLayout favoriteSelected = null;
    private ConstraintLayout filterActive;
    private ConstraintLayout iconFilterActive;
    private TextView textViewActive;

    @SuppressLint({"ResourceAsColor", "WrongViewCast"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        lnListFavorites = findViewById(R.id.lnListFavorites);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        filterActive= findViewById(R.id.blockBtnAllTypes);
        iconFilterActive = findViewById(R.id.btnAllTypes);
        textViewActive = findViewById(R.id.txBtnAllTypes);
        /*DbHelper db = new DbHelper(FavoriteActivity.this);
        List<Favorite> favoriteList = db.getAllFavorites();
        Log.d("Test",favoriteList.toString());
        String[] routes = new String[favoriteList.size()];
        for(int i=0;i<favoriteList.size();++i) {
            routes[i]=favoriteList.get(i).getLocation();
            routes[i]=favoriteList.get(i).getDestination();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,routes);
        spinnerFavorite.setAdapter(arrayAdapter);
        if(favoriteList.size()>0) {
            homeActivity.etLocation.setText(favoriteList.get(0).getLocation());
            homeActivity.etDestination.setText(favoriteList.get(0).getDestination());
        }
        spinnerFavorite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Favorite> plantList = db.getAllFavorites();
                //homeActivity.etLocation.setText(plantList.get(position).getLocation());
                //homeActivity.etDestination.setText(plantList.get(position).getDestination());
                iFavoriteId = plantList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(FavoriteActivity.this);
                //String strLocation = etLocation.getText().toString();
                //String strDestination = etDestination.getText().toString();
                String strLocation = "";
                String strDestination = "";
                // Get it from DB
                Favorite favorite = new Favorite(strLocation, strDestination);
                favorite.setId(iFavoriteId);
                db.deleteFavorite(favorite);
                Intent i = new Intent(FavoriteActivity.this,HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 2","Rota 3", LocalDate.now()
                ,2,"8h","EUR 50"));
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 4","Rota 5",LocalDate.now()
                ,1,"3h","EUR 100"));
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 6","Rota 7",LocalDate.now()
                ,3,"15h","EUR 20"));*/
        try {
            listFavorites = SQLiteMan.getListFavorites();
            constructListFavorites(listFavorites);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterAll(View view){
        if(view.getId()!=filterActive.getId()){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.shape_arredounded_blue);
            ConstraintLayout icon = findViewById(R.id.btnAllTypes);
            TextView txBtn = findViewById(R.id.txBtnAllTypes);
            icon.setBackgroundResource(R.drawable.ic_baseline_apps_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            lnListFavorites.removeAllViews();
            try{
                constructListFavorites(SQLiteMan.getListFavorites());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterBus(View view){
        if(view.getId()!=filterActive.getId()){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.shape_arredounded_blue);
            ConstraintLayout icon = findViewById(R.id.btnBusType);
            TextView txBtn = findViewById(R.id.txBtnBusType);
            icon.setBackgroundResource(R.drawable.ic_baseline_directions_bus_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            lnListFavorites.removeAllViews();
            try{
                constructListFavorites(SQLiteMan.getListFavoritesWithTransportType("2"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterTrain(View view){
        if(view.getId()!=filterActive.getId()){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.shape_arredounded_blue);
            ConstraintLayout icon = findViewById(R.id.btnTrainType);
            TextView txBtn = findViewById(R.id.txBtnTrainType);
            icon.setBackgroundResource(R.drawable.ic_baseline_train_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            lnListFavorites.removeAllViews();
            try{
                constructListFavorites(SQLiteMan.getListFavoritesWithTransportType("1"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterLuas(View view){
        if(view.getId()!=filterActive.getId()){
            clearLastFilter();
            view.setBackgroundResource(R.drawable.shape_arredounded_blue);
            ConstraintLayout icon = findViewById(R.id.btnLuasType);
            TextView txBtn = findViewById(R.id.txBtnLuasType);
            icon.setBackgroundResource(R.drawable.ic_baseline_subway_white_24);
            txBtn.setTextColor(Color.rgb(255,250,250));
            filterActive = (ConstraintLayout) view;
            textViewActive = txBtn;
            iconFilterActive = icon;
            lnListFavorites.removeAllViews();
            try{
                constructListFavorites(SQLiteMan.getListFavoritesWithTransportType("3"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("ResourceType")
    public void clearLastFilter(){
        filterActive.setBackgroundResource(R.drawable.shape_arredounded);
        if(textViewActive.getText().equals("All")){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_apps_24);
        }else if(textViewActive.getText().equals("Train")){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_train_24);
        }else if(textViewActive.getText().equals("Bus")){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_directions_bus_24);
        }else if(textViewActive.getText().equals("Luas")){
            iconFilterActive.setBackgroundResource(R.drawable.ic_baseline_subway_24);
        }
        textViewActive.setTextColor(Color.rgb(56,56,56));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteFavorite(View view){
        if(favoriteSelected!=null){
            @SuppressLint("ResourceType") TextView tx = (TextView) favoriteSelected.findViewById(favoriteSelected.getId()+5);
            SQLiteMan.removeFavoriteById(Integer.parseInt(tx.getText()+""));
            lnListFavorites.removeView(favoriteSelected);
        }
    }
    public void constructListFavorites(List<TravelDTO> list){
        int i = 0;
        for(TravelDTO tv:list) {
            ConstraintLayout ct = new ConstraintLayout(getApplicationContext());
            ct.setBackgroundResource(R.drawable.shape_arredounded);
            ct.setId(i++);
            ct.setPadding(5, 5, 5, 5);
            ct.setMinWidth(lnListFavorites.getWidth());
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 50);
            ct.setLayoutParams(layoutParams);
            ct.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    if (favoriteSelected != null) {
                        favoriteSelected.setBackgroundResource(R.drawable.shape_arredounded);
                        ImageView iconTransportType = (ImageView) favoriteSelected.getViewById(favoriteSelected.getId() + 1);
                        TextView cdTransportType = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 6);
                        iconTransportType.setImageResource(castingCdTypeTransport(Integer.parseInt(cdTransportType.getText() + ""),
                                "default"));
                        TextView txLocation = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 2);
                        txLocation.setTextColor(Color.rgb(56, 56, 56));
                        TextView txDestine = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 4);
                        txDestine.setTextColor(Color.rgb(56, 56, 56));
                    }
                    view.setBackgroundResource(R.drawable.shape_arredounded_blue);
                    favoriteSelected = (ConstraintLayout) view;
                    ImageView iconTransportType = (ImageView) favoriteSelected.getViewById(favoriteSelected.getId() + 1);
                    TextView cdTransportType = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 6);
                    iconTransportType.setImageResource(castingCdTypeTransport(Integer.parseInt(cdTransportType.getText() + ""),
                            "white"));
                    TextView txLocation = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 2);
                    txLocation.setTextColor(Color.rgb(255, 250, 250));
                    TextView txDestine = (TextView) favoriteSelected.getViewById(favoriteSelected.getId() + 4);
                    txDestine.setTextColor(Color.rgb(255, 250, 250));
                }
            });
            ct.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(FavoriteActivity.this, PaymentActivity.class);
                    intent.putExtra("titleTicket",tv.getDsTitleTicket());
                    intent.putExtra("locationName",tv.getLocation());
                    intent.putExtra("locationTime",tv.getDtHourDeparture());
                    intent.putExtra("destinationName",tv.getDestiny());
                    intent.putExtra("destinationTime",tv.getDtHourTravel());
                    intent.putExtra("TimeTicket",tv.getDtDuration()
                            .replaceAll("\\shour[s]+\\s",".")
                            .replaceAll("\\smin[s]+","m"));
                    intent.putExtra("numberPersons", tv.getNumPassengers().toString());
                    intent.putExtra("imgTypeTransport",tv.getCdTransport());
                    //intent.putExtra("favoriteSelection",favoriteSelection);
                    //PRICE NÃO ESTÁ FORMATADO EM CASO DO FARE VIR
                    intent.putExtra("value", tv.getValue()+"");
                    intent.putExtra("ticketPrice",tv.getValue().toString().substring(0,
                            tv.getValue().toString().length()-2));
                    startActivity(intent);
                    return true;
                }
            });

            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(castingCdTypeTransport(tv.getCdTransport(), "default"));
            layoutParams = new ConstraintLayout.LayoutParams(100, 100);
            layoutParams.baselineToBottom = ct.getId();
            layoutParams.baselineToTop = ct.getId();
            layoutParams.leftToLeft = ct.getId();
            layoutParams.rightToRight = ct.getId();
            layoutParams.horizontalBias = (float) 0.075;
            //layoutParams.rightMargin = 8;
            img.setLayoutParams(layoutParams);
            //+1
            img.setId(i++);
            ct.addView(img);

            TextView txLocation = new TextView(getApplicationContext());
            txLocation.setText(tv.getLocation());
            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layoutParams.leftMargin = 80;
            layoutParams.baselineToBottom = ct.getId();
            layoutParams.baselineToTop = ct.getId();
            layoutParams.verticalBias = (float) 0.508;
            layoutParams.startToEnd = img.getId();
            txLocation.setTextColor(Color.rgb(56, 56, 56));
            txLocation.setTextSize(18);
            txLocation.setLayoutParams(layoutParams);
            //+2
            txLocation.setId(i++);
            ct.addView(txLocation);

            ImageView imgArrow = new ImageView(getApplicationContext());
            imgArrow.setImageResource(R.drawable.ic_baseline_arrow_right_alt_24);
            //+3
            imgArrow.setId(i++);
            layoutParams = new ConstraintLayout.LayoutParams(100, 100);
            layoutParams.leftMargin = 100;
            layoutParams.rightMargin = 100;
            layoutParams.baselineToBottom = ct.getId();
            layoutParams.baselineToTop = ct.getId();
            layoutParams.startToEnd = txLocation.getId();
            imgArrow.setLayoutParams(layoutParams);
            ct.addView(imgArrow);

            TextView txDestination = new TextView(getApplicationContext());
            txDestination.setText(tv.getDestiny());
            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layoutParams.baselineToBottom = ct.getId();
            layoutParams.baselineToTop = ct.getId();
            layoutParams.startToEnd = imgArrow.getId();
            layoutParams.leftMargin = 80;
            //layoutParams.startToStart = ct.getId();
            txDestination.setLayoutParams(layoutParams);
            //+4
            txDestination.setId(i++);
            txDestination.setTextColor(Color.rgb(56, 56, 56));
            txDestination.setTextSize(18);
            ct.addView(txDestination);

            TextView txId = new TextView(getApplicationContext());
            txId.setText(String.valueOf(tv.getId()));
            //+5
            txId.setId(i++);
            txId.setVisibility(View.INVISIBLE);
            //txId.setLayoutParams(layoutParams);
            ct.addView(txId);

            TextView txTypeTransport = new TextView(getApplicationContext());
            txTypeTransport.setText(String.valueOf(tv.getCdTransport()));
            //+6
            txTypeTransport.setId(i++);
            txTypeTransport.setVisibility(View.INVISIBLE);
            //txId.setLayoutParams(layoutParams);
            ct.addView(txTypeTransport);
            lnListFavorites.addView(ct);
        }
    }
    public void menuButton(View view) {
        Intent intent = new Intent(FavoriteActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void backButton(View view) {
        Intent intent = new Intent(FavoriteActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public Integer castingCdTypeTransport(Integer cd,String color){
        if(color == "default"){
            switch (cd){
                case 1:
                    return R.drawable.ic_baseline_train_24;
                case 2:
                    return R.drawable.ic_baseline_directions_bus_24;
                case 3:
                    return R.drawable.ic_baseline_subway_24;
            }
        }else if(color == "white"){
            switch (cd){
                case 1:
                    return R.drawable.ic_baseline_train_white_24;
                case 2:
                    return R.drawable.ic_baseline_directions_bus_white_24;
                case 3:
                    return R.drawable.ic_baseline_subway_white_24;
            }
        }
        return null;
    }
}