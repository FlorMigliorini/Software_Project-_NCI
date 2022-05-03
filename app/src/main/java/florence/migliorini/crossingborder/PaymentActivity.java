package florence.migliorini.crossingborder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.googlepaylauncher.GooglePayEnvironment;
import com.stripe.android.googlepaylauncher.GooglePayLauncher;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import florence.migliorini.db.SQLiteMan;
import florence.migliorini.model.StripeKeyDTO;
import florence.migliorini.model.TravelDTO;
import florence.migliorini.payment.ConfigStripe;

public class PaymentActivity extends AppCompatActivity {
    PaymentSheet paymentSheet;
    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;
    ConfigStripe config = new ConfigStripe();
    Gson gson = new Gson();
    StripeKeyDTO strpe ;
    private Button btnStripe, btnGooglePlayPay;
    private TextView titleTicket, locationName, locationTime, destinationName,
            destinationTime, TimeTicket, numberPersons,timeTicketPay;
    private Integer typeTransport;
    private ImageView imgTypeTransport;
    private String ticketPrice;
    private Integer favoriteSelection;
    private Integer valueTicket;
    private String dateTimePay;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        timeTicketPay = findViewById(R.id.timeTicketPay);
        Intent in = getIntent();
        titleTicket.setText(in.getStringExtra("titleTicket"));
        locationName.setText(in.getStringExtra("locationName"));
        locationTime.setText(in.getStringExtra("locationTime"));
        destinationName.setText(in.getStringExtra("destinationName"));
        destinationTime.setText(in.getStringExtra("destinationTime"));
        TimeTicket.setText(in.getStringExtra("TimeTicket"));
        numberPersons.setText(in.getStringExtra("numberPersons"));
        typeTransport = in.getIntExtra("imgTypeTransport",0);
        favoriteSelection = in.getIntExtra("favoriteSelection",0);
        dateTimePay = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        timeTicketPay.setText(dateTimePay);
        valueTicket = Integer.parseInt(in.getStringExtra("value"));
        strpe = gson.fromJson(config.getKey(Long.parseLong(in.getStringExtra("value")))
                ,StripeKeyDTO.class);
        ticketPrice = in.getStringExtra("ticketPrice");
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        PaymentConfiguration.init(this, strpe.getPublishableKey());
        configPaymentWithGooglePlay();
        configTransportIconType(in.getIntExtra("imgTypeTransport",0));
    }
    //Alteração do icone do ticket baseado na código do transporte por exemplo (1 = train etc...)
    public void configTransportIconType(Integer type){
        imgTypeTransport.setImageResource(castingCdTypeTransport(type));
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
    //Configura o botão de pagamento com Google pay
    /**
     * O método de pagamento com o google pay foi utilizado
     * baseado na documentação do Stripe
     * https://stripe.com/docs/google-pay
     *
     * **/
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

    private void onGooglePayResult(GooglePayLauncher.Result result) {
        // implemented below
    }
    @Override
    protected void onStart() {
        super.onStart();
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
    //Abre o menu.
    public void menuButton(View view) {
        Intent intent = new Intent(PaymentActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    //Configura o Pagamento com cartão.
    /**
     * O método a seguir configura e cria uma intent para o método de pagamento do Stripe
     * https://stripe.com/docs/payments
     * **/
    private void fetchPaymentIntent() {
        paymentIntentClientSecret = strpe.getPaymentIntentClientSecret();
        customerConfig = new PaymentSheet.CustomerConfiguration(
                strpe.getCustomer(),
                strpe.getEphemeralKey()
        );
        PaymentConfiguration.init(getApplicationContext(),strpe.getPublishableKey());
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration);
    }

    //Tratamento do pagamento confirmado ou cancelado.
    /**
     * O método a seguir executa ações para o retorno do Stripe
     * Caso o pagamento seja confirmado o método executa uma intent para a
     * Activity responsavel por gerar o QrCode passando as informações necessarias.
     *
     * Também é adicionado ao histórico a compra e aos favoritos (se selecionado)
     * utilizando um banco local SQLite
     * https://www.sqlite.org/docs.html
     * **/
    @RequiresApi(api = Build.VERSION_CODES.O)
    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d("Stripe","Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            showAlert("Erro","Ocorreu um erro inesperado ao tentar executar o pagamento");
            Log.e("Stripe", "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Intent intent = new Intent(PaymentActivity.this,PlanActivity.class);
            intent.putExtra("titleTicket",titleTicket.getText());
            intent.putExtra("locationName",locationName.getText());
            intent.putExtra("locationTime",locationTime.getText());
            intent.putExtra("destinationName",destinationName.getText());
            intent.putExtra("destinationTime",destinationTime.getText());
            intent.putExtra("TimeTicket",TimeTicket.getText().toString());
            intent.putExtra("dateTicketPlan",dateTimePay);
            intent.putExtra("numberPersons",numberPersons.getText().toString());
            intent.putExtra("imgTypeTransport",typeTransport);
            TravelDTO travel = new TravelDTO(null,locationName.getText().toString(),destinationName.getText().toString()
                    , LocalDate.now(),typeTransport,dateTimePay,valueTicket,
                    TimeTicket.getText().toString(),titleTicket.getText().toString(),
                    locationTime.getText().toString(),destinationTime.getText().toString()
                    ,Integer.parseInt(numberPersons.getText().toString()));
            if(favoriteSelection == 1){
                SQLiteMan.getInstance(getApplicationContext(),"database").addFavorite(travel);
            }
            SQLiteMan.getInstance(getApplicationContext(),"database")
                    .addHistoric(travel);
            startActivity(intent);
            finish();
        }
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
}