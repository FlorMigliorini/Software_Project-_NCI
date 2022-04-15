package florence.migliorini.crossingborder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import androidx.appcompat.app.AlertDialog;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.model.issuing.Transaction;
import florence.migliorini.model.StripeKeyDTO;
import florence.migliorini.payment.ConfigStripe;


public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";
    private Button payButton;
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_checkout);
        //payButton = findViewById(R.id.pay_button);
        //payButton.setOnClickListener(this::onPayClicked);
        //payButton.setEnabled(false);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        fetchPaymentIntent();
    }
    private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }

    private void fetchPaymentIntent() {
        final String shoppingCartContent = "{\"items\": [ {\"id\":\"xl-tshirt\"}]}";
        Gson gson = new Gson();
        ConfigStripe config = new ConfigStripe();
        StripeKeyDTO strpe = gson.fromJson(config.getKey(),StripeKeyDTO.class);
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
    private void onPayClicked(View view) {
        /*PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);*/
    }
}
