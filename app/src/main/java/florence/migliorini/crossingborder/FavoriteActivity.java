package florence.migliorini.crossingborder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static androidx.constraintlayout.widget.ConstraintProperties.PARENT_ID;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import florence.migliorini.db.DbFavorite;
import florence.migliorini.db.DbHelper;
import florence.migliorini.db.SQLiteMan;
import florence.migliorini.model.Favorite;
import florence.migliorini.model.TravelDTO;

public class FavoriteActivity extends AppCompatActivity {

    private Button btnDelete,btnBack;
    private List<TravelDTO> listFavorites;
    private LinearLayout lnListFavorites;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        lnListFavorites = findViewById(R.id.lnListFavorites);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
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
        });*/
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 2","Rota 3",new Date()
                ,2,"8h","EUR 50"));
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 4","Rota 5",new Date()
                ,1,"3h","EUR 100"));
        SQLiteMan.addFavorite(new TravelDTO(null,"Rota 6","Rota 7",new Date()
                ,3,"15h","EUR 20"));
        try {
            listFavorites = SQLiteMan.getListFavorites();
            int i = 0;
            for(TravelDTO tv:listFavorites){
                ConstraintLayout ct = new ConstraintLayout(getApplicationContext());
                ct.setBackgroundResource(R.drawable.shape_arredounded);
                ct.setId(i++);
                ct.setMinWidth(lnListFavorites.getWidth());
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,50);
                ct.setLayoutParams(layoutParams);

                ImageView img = new ImageView(getApplicationContext());
                img.setImageResource(R.drawable.ic_baseline_directions_bus_24);
                layoutParams = new ConstraintLayout.LayoutParams(100,100);
                layoutParams.baselineToBottom = ct.getId();
                layoutParams.baselineToTop = ct.getId();
                layoutParams.leftToLeft = ct.getId();
                layoutParams.rightToRight = ct.getId();
                layoutParams.horizontalBias = (float) 0.075;
                //layoutParams.rightMargin = 8;
                img.setLayoutParams(layoutParams);
                img.setId(i++);
                ct.addView(img);

                TextView txLocation = new TextView(getApplicationContext());
                txLocation.setText(tv.getLocation());
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
                layoutParams.leftMargin = 80;
                layoutParams.baselineToBottom = ct.getId();
                layoutParams.baselineToTop = ct.getId();
                layoutParams.verticalBias = (float) 0.508;
                layoutParams.startToEnd = img.getId();
                txLocation.setTextColor(R.color.black);
                txLocation.setTextSize(18);
                txLocation.setLayoutParams(layoutParams);
                txLocation.setId(i++);
                ct.addView(txLocation);

                ImageView imgArrow = new ImageView(getApplicationContext());
                imgArrow.setImageResource(R.drawable.ic_baseline_arrow_right_alt_24);
                imgArrow.setId(i++);
                layoutParams = new ConstraintLayout.LayoutParams(100,100);
                layoutParams.leftMargin = 100;
                layoutParams.rightMargin = 100;
                layoutParams.baselineToBottom = ct.getId();
                layoutParams.baselineToTop = ct.getId();
                layoutParams.startToEnd = txLocation.getId();
                imgArrow.setLayoutParams(layoutParams);
                ct.addView(imgArrow);

                TextView txDestination = new TextView(getApplicationContext());
                txDestination.setText(tv.getDestiny());
                layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
                layoutParams.baselineToBottom = ct.getId();
                layoutParams.baselineToTop = ct.getId();
                layoutParams.startToEnd = imgArrow.getId();
                layoutParams.leftMargin = 80;
                //layoutParams.startToStart = ct.getId();
                txDestination.setLayoutParams(layoutParams);
                txDestination.setId(i++);
                txDestination.setTextColor(R.color.black);
                txDestination.setTextSize(18);
                ct.addView(txDestination);
                lnListFavorites.addView(ct);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}