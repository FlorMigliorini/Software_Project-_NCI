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


public class QrCodeActivity extends AppCompatActivity {
    private Button btnBack;
    private String qrCodeCoded;
    private TextView titleTicket,locationName,locationTime,destinationName,destinationTime,TimeTicket,numberPersons,dateTicketPlan;
    private ImageView imgQr,imgTypeTransport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        imgQr = findViewById(R.id.imgBlockQrCode);
        imgTypeTransport = findViewById(R.id.iconTypeTransport);
        titleTicket = findViewById(R.id.TitleTicket);
        locationName = findViewById(R.id.locationName);
        locationTime = findViewById(R.id.locationTime);
        destinationName = findViewById(R.id.destinationName);
        destinationTime = findViewById(R.id.destinationTime);
        TimeTicket = findViewById(R.id.timeTicket);
        numberPersons = findViewById(R.id.numberOfPerson);
        dateTicketPlan = findViewById(R.id.dateTicketPlan);
        Intent in = getIntent();
        titleTicket.setText(in.getStringExtra("titleTicket"));
        locationName.setText(in.getStringExtra("locationName"));
        locationTime.setText(in.getStringExtra("locationTime"));
        destinationName.setText(in.getStringExtra("destinationName"));
        destinationTime.setText(in.getStringExtra("destinationTime"));
        TimeTicket.setText(in.getStringExtra("TimeTicket"));
        numberPersons.setText(in.getStringExtra("numberPersons"));
        dateTicketPlan.setText(in.getStringExtra("dateTicketPlan"));

        qrCodeCoded = "";
        qrCodeCoded += in.getStringExtra("titleTicket");
        qrCodeCoded += in.getStringExtra("locationName");
        qrCodeCoded += in.getStringExtra("locationTime");
        qrCodeCoded += in.getStringExtra("destinationName");
        qrCodeCoded += in.getStringExtra("destinationTime");
        qrCodeCoded += in.getStringExtra("TimeTicket");
        qrCodeCoded += in.getStringExtra("numberPersons");
        imgTypeTransport.setImageResource(castingCdTypeTransport(in.getIntExtra("imgTypeTransport",0)));
    }

    //Inicia a Activity e cria o QrCode
    /**
     * O método a seguir inicia a activity e utiliza dos atributos ja iniciados
     * para criar um QRcode
     * a biblioteca utilizada para criar o qrCode é
     * https://github.com/journeyapps/zxing-android-embedded
     * **/
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
        Intent intent = new Intent(QrCodeActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void backButton(View view) {
        Intent intent = new Intent(QrCodeActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public Integer castingCdTypeTransport(Integer cd){
        switch (cd){
            case 1:
                return R.drawable.ic_baseline_train_24;
            case 2:
                return R.drawable.ic_baseline_directions_bus_24;
            case 3:
                return R.drawable.ic_baseline_subway_24;
        }
        return null;
    }
}