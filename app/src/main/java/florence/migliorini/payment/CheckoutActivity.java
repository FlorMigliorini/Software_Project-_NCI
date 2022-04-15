package florence.migliorini.payment;

import android.content.Context;
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


public class CheckoutActivity{
    private static final String TAG = "CheckoutActivity";
    private Button payButton;
    PaymentSheet paymentSheet;
    private Context context;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    /*public CheckoutActivity(PaymentActivity instance, Context context){
        //setContentView(R.layout.activity_checkout);
        //payButton = findViewById(R.id.pay_button);
        //payButton.setOnClickListener(this::onPayClicked);
        //payButton.setEnabled(false);
        this.context = context;
        paymentSheet = new PaymentSheet(instance, this::onPaymentSheetResult);

        fetchPaymentIntent();
    }*/
    /*private void showAlert(String title, @Nullable String message) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Ok", null)
                    .create();
            dialog.show();
        });
    }*/

    /*private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_LONG).show());
    }*/


    private void onPayClicked(View view) {
        /*PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);*/
    }
}
