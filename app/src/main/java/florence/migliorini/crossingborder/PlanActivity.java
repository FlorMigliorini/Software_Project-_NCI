package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

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
    private Button bt;
    private TextView tx;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        img = findViewById(R.id.imgBlockQrCode);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String texto = "Exemplo";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(texto, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder brEncod = new BarcodeEncoder();
            Bitmap bit = brEncod.createBitmap(bitMatrix);
            img.setImageBitmap(bit);
        }catch(WriterException e){
            e.printStackTrace();
        }
    }
}