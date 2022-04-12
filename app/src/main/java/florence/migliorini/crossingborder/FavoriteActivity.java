package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import florence.migliorini.db.DbHelper;
import florence.migliorini.model.Favorite;

public class FavoriteActivity extends AppCompatActivity {

    Button btnDelete;
    HomeActivity homeActivity;
    int iFavoriteId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Spinner spinnerFavorite = findViewById(R.id.spinner);
        btnDelete = findViewById(R.id.btnDelete);

        DbHelper db = new DbHelper(FavoriteActivity.this);
        List<Favorite> favoriteList = db.getAllFavorites();
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
                homeActivity.etLocation.setText(plantList.get(position).getLocation());
                homeActivity.etDestination.setText(plantList.get(position).getDestination());
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

    }


}