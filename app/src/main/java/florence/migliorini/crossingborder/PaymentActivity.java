package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    private Button btnStripe,btnGooglePlayPay;
    private TextView titleTicket,locationName,locationTime,destinationName,destinationTime,TimeTicket,numberPersons;
    private ImageView imgTypeTransport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        imgTypeTransport = findViewById(R.id.iconTypeTransport);
        titleTicket = findViewById(R.id.TitleTicket);
        locationName = findViewById(R.id.locationName);
        locationTime = findViewById(R.id.locationTime);
        destinationName = findViewById(R.id.destinationName);
        destinationTime = findViewById(R.id.destinationTime);
        TimeTicket = findViewById(R.id.timeTicket);
        numberPersons = findViewById(R.id.numberOfPerson);
        btnStripe = findViewById(R.id.btnPaymentStripe);
        btnGooglePlayPay = findViewById(R.id.btnPaymentGooglePlay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Intent intent = new Intent(PaymentActivity.this,PlanActivity.class);
        startActivity(intent);
        finish();*/
        btnStripe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void menuButton(View view) {
        Intent intent = new Intent(PaymentActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}