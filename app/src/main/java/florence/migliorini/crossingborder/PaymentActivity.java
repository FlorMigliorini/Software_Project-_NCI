package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(PaymentActivity.this,PlanActivity.class);
        startActivity(intent);
        finish();
    }
}