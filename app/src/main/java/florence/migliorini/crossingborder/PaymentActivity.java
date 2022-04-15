package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.googlepaylauncher.GooglePayEnvironment;
import com.stripe.android.googlepaylauncher.GooglePayLauncher;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import florence.migliorini.model.StripeKeyDTO;
import florence.migliorini.payment.ConfigStripe;

public class PaymentActivity extends AppCompatActivity {
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    ConfigStripe config = new ConfigStripe();
    Gson gson = new Gson();
    StripeKeyDTO strpe = gson.fromJson(config.getKey(),StripeKeyDTO.class);
    private Button btnStripe, btnGooglePlayPay;
    private TextView titleTicket, locationName, locationTime, destinationName, destinationTime, TimeTicket, numberPersons;
    private ImageView imgTypeTransport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        PaymentConfiguration.init(this, strpe.getPublishableKey());
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
        configPaymentWithGooglePlay();
    }
    public void configPaymentWithGooglePlay(){
        final GooglePayLauncher googlePayLauncher = new GooglePayLauncher(
                this,
                new GooglePayLauncher.Config(
                        GooglePayEnvironment.Test,
                        "US",
                        "Widget Store"
                ),
                this::onGooglePayReady,
                this::onGooglePayResult
        );
        btnGooglePlayPay.setOnClickListener(
                v -> googlePayLauncher.presentForPaymentIntent(strpe.getPaymentIntentClientSecret())
        );
    }
    private void onGooglePayReady(boolean isReady) {
        // implemented below
    }

    private void onGooglePayResult(@NotNull GooglePayLauncher.Result result) {
        // implemented below
    }
    @Override
    protected void onStart() {
        super.onStart();
        /*Intent intent = new Intent(PaymentActivity.this,PlanActivity.class);
        startActivity(intent);
        finish();*/
        PaymentActivity instance = this;
        btnStripe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(PaymentActivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();*/
                fetchPaymentIntent();
            }
        });
    }

    public void menuButton(View view) {
        Intent intent = new Intent(PaymentActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchPaymentIntent() {
        final String shoppingCartContent = "{\"items\": [ {\"id\":\"xl-tshirt\"}]}";
        paymentIntentClientSecret = strpe.getPaymentIntentClientSecret();
        customerConfig = new PaymentSheet.CustomerConfiguration(
                strpe.getCustomer(),
                strpe.getEphemeralKey()
        );
        PaymentConfiguration.init(getApplicationContext(),strpe.getPublishableKey());
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
        //runOnUiThread(() -> payButton.setEnabled(true));
    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d("Stripe","Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e("Stripe", "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            // Display for example, an order confirmation screen
            Log.d("Stripe","Completed");
        }
    }
    private void presentPaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Example, Inc.")
                .customer(customerConfig)
                // Set `allowsDelayedPaymentMethods` to true if your business can handle payment methods
                // that complete payment after a delay, like SEPA Debit and Sofort.
                .allowsDelayedPaymentMethods(true)
                .build();
        //TEM UM ERRO AQUI
        String paymentClientSecret = "sk_test_51KoodkK4jThQYIzhMivdqlsLJGCgcGDSpUoDPIh3bZTMdIWK8t30S1wVyrBMom78wWLwlAqVumgB6Li56HykEveD00cfvfIQA5";
        paymentSheet.presentWithPaymentIntent(
                paymentClientSecret,
                configuration
        );
    }

}