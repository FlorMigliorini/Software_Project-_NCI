package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class PlanActivity extends AppCompatActivity {
    private Button btnBack;
    private String qrCodeCoded;
    private TextView titleTicket,locationName,locationTime,destinationName,destinationTime,TimeTicket,numberPersons;
    private ImageView imgQr,imgTypeTransport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        imgQr = findViewById(R.id.imgBlockQrCode);
        imgTypeTransport = findViewById(R.id.iconTypeTransport);
        titleTicket = findViewById(R.id.TitleTicket);
        locationName = findViewById(R.id.locationName);
        locationTime = findViewById(R.id.locationTime);
        destinationName = findViewById(R.id.destinationName);
        destinationTime = findViewById(R.id.destinationTime);
        TimeTicket = findViewById(R.id.timeTicket);
        numberPersons = findViewById(R.id.numberOfPerson);
        Intent in = getIntent();
        titleTicket.setText(in.getStringExtra("titleTicket"));
        locationName.setText(in.getStringExtra("locationName"));
        locationTime.setText(in.getStringExtra("locationTime"));
        destinationName.setText(in.getStringExtra("destinationName"));
        destinationTime.setText(in.getStringExtra("destinationTime"));
        TimeTicket.setText(in.getStringExtra("TimeTicket"));
        numberPersons.setText(in.getStringExtra("numberPersons"));

        qrCodeCoded = "";
        qrCodeCoded += in.getStringExtra("titleTicket");
        qrCodeCoded += in.getStringExtra("locationName");
        qrCodeCoded += in.getStringExtra("locationTime");
        qrCodeCoded += in.getStringExtra("destinationName");
        qrCodeCoded += in.getStringExtra("destinationTime");
        qrCodeCoded += in.getStringExtra("TimeTicket");
        qrCodeCoded += in.getStringExtra("numberPersons");

    }
    public String convertIconTypeTransport(Integer type){
        switch (type){
            case 1:
                return "bus";
            case 2:
                return "train";
            case 3:
                return "luas";
        }
        return null;
    }
    @Override
    protected void onStart() {
        super.onStart();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(qrCodeCoded, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder brEncod = new BarcodeEncoder();
            Bitmap bit = brEncod.createBitmap(bitMatrix);
            imgQr.setImageBitmap(bit);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }

    public void menuButton(View view) {
        Intent intent = new Intent(PlanActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void backButton(View view) {
        Intent intent = new Intent(PlanActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}