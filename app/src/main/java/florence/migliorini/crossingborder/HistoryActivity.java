package florence.migliorini.crossingborder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

import florence.migliorini.db.SQLiteMan;
import florence.migliorini.model.TravelDTO;

public class HistoryActivity extends AppCompatActivity {
    private Button btnDelete,btnBack;
    private List<TravelDTO> listHistoric;
    private LinearLayout lnListHistoric;
    private ConstraintLayout historicSelected = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lnListHistoric = findViewById(R.id.lnListHistory);
        /*SQLiteMan.addHistoric(new TravelDTO(null,"Rota 2","Rota 3", LocalDate.now()
                ,2,"8h","EUR 50"));
        SQLiteMan.addHistoric(new TravelDTO(null,"Rota 4","Rota 5",LocalDate.now()
                ,1,"3h","EUR 100"));
        SQLiteMan.addHistoric(new TravelDTO(null,"Rota 6","Rota 7",LocalDate.now()
                ,3,"15h","EUR 20"));*/
        listHistoric = SQLiteMan.getListHistoric();
        constructListFavorites(listHistoric);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void constructListFavorites(List<TravelDTO> list){
        int i = 0;
        for(TravelDTO tv:list) {
            ConstraintLayout ct = new ConstraintLayout(getApplicationContext());
            ct.setBackgroundResource(R.drawable.shape_arredounded);
            ct.setId(i++);
            ct.setPadding(5, 5, 5, 5);
            ct.setMinWidth(lnListHistoric.getWidth());
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 50);
            ct.setLayoutParams(layoutParams);
            ct.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    if (historicSelected != null) {
                        historicSelected.setBackgroundResource(R.drawable.shape_arredounded);
                        ImageView iconTransportType = (ImageView) historicSelected.getViewById(historicSelected.getId() + 1);
                        TextView cdTransportType = (TextView) historicSelected.getViewById(historicSelected.getId() + 6);
                        iconTransportType.setImageResource(castingCdTypeTransport(Integer.parseInt(cdTransportType.getText() + ""),
                                "default"));
                        TextView txLocation = (TextView) historicSelected.getViewById(historicSelected.getId() + 2);
                        txLocation.setTextColor(Color.rgb(56, 56, 56));
                        TextView txDestine = (TextView) historicSelected.getViewById(historicSelected.getId() + 4);
                        txDestine.setTextColor(Color.rgb(56, 56, 56));
                    }
                    view.setBackgroundResource(R.drawable.shape_arredounded_blue);
                    historicSelected = (ConstraintLayout) view;
                    ImageView iconTransportType = (ImageView) historicSelected.getViewById(historicSelected.getId() + 1);
                    TextView cdTransportType = (TextView) historicSelected.getViewById(historicSelected.getId() + 6);
                    iconTransportType.setImageResource(castingCdTypeTransport(Integer.parseInt(cdTransportType.getText() + ""),
                            "white"));
                    TextView txLocation = (TextView) historicSelected.getViewById(historicSelected.getId() + 2);
                    txLocation.setTextColor(Color.rgb(255, 250, 250));
                    TextView txDestine = (TextView) historicSelected.getViewById(historicSelected.getId() + 4);
                    txDestine.setTextColor(Color.rgb(255, 250, 250));
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

            TextView txDate = new TextView(getApplicationContext());
            txDate.setText(tv.getDtInitial().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            //txDate.setVisibility(View.INVISIBLE);
            layoutParams = new ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            //layoutParams.baselineToBottom = imgArrow.getId();
            layoutParams.topToBottom = imgArrow.getId();
            //layoutParams.baselineToTop = imgArrow.getId();
            layoutParams.startToEnd = img.getId();
            //layoutParams.leftMargin = 80;
            txDate.setLayoutParams(layoutParams);
            txDate.setTextSize(17);
            ct.addView(txDate);

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
            lnListHistoric.addView(ct);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteHistory(View view){
        if(historicSelected!=null){
            @SuppressLint("ResourceType") TextView tx = (TextView) historicSelected.findViewById(historicSelected.getId()+5);
            SQLiteMan.removeFavoriteById(Integer.parseInt(tx.getText()+""));
            lnListHistoric.removeView(historicSelected);
        }
    }
    public void menuButton(View view) {
        Intent intent = new Intent(HistoryActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void backButton(View view) {
        Intent intent = new Intent(HistoryActivity.this, HomeActivity.class);
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