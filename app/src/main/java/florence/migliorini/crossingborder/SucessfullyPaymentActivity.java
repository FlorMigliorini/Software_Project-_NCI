package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SucessfullyPaymentActivity extends AppCompatActivity {
    private Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        in = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucessfully_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SucessfullyPaymentActivity.this, QrCodeActivity.class);
                intent.putExtra("titleTicket",in.getStringExtra("titleTicket"));
                intent.putExtra("locationName",in.getStringExtra("locationName"));
                intent.putExtra("locationTime",in.getStringExtra("locationTime"));
                intent.putExtra("destinationName",in.getStringExtra("destinationName"));
                intent.putExtra("destinationTime",in.getStringExtra("destinationTime"));
                intent.putExtra("TimeTicket",in.getStringExtra("TimeTicket"));
                intent.putExtra("dateTicketPlan",in.getStringExtra("dateTicketPlan"));
                intent.putExtra("numberPersons",in.getStringExtra("numberPersons"));
                intent.putExtra("imgTypeTransport",in.getIntExtra("imgTypeTransport",0));
                startActivity(intent);
                finish();
            }
        }, 4000L);
    }
}