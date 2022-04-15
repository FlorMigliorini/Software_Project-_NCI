package florence.migliorini.payment;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigStripe {
    public ConfigStripe(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Stripe.apiKey = "sk_test_51KoodkK4jThQYIzhMivdqlsLJGCgcGDSpUoDPIh3bZTMdIWK8t30S1wVyrBMom78wWLwlAqVumgB6Li56HykEveD00cfvfIQA5";
    }

    public String getKey(){
        try{
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .build();
            Customer customer = Customer.create(customerParams);
            EphemeralKeyCreateParams ephemeralKeyParams =
                    EphemeralKeyCreateParams.builder()
                            .setCustomer(customer.getId())
                            .build();
            RequestOptions ephemeralKeyOptions = new RequestOptions.RequestOptionsBuilder().setStripeVersionOverride("2020-08-27")
                    .build();
            EphemeralKey ephemeralKey = EphemeralKey.create(
                    ephemeralKeyParams,
                    ephemeralKeyOptions);
            PaymentIntentCreateParams paymentIntentParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(100L)
                            .setCurrency("eur")
                            .setCustomer(customer.getId())
                            .setAutomaticPaymentMethods(new PaymentIntentCreateParams
                                    .AutomaticPaymentMethods.Builder()
                                    .setEnabled(true)
                                    .build())
                            //.setPaymentMethod("card")
                            //.setPaymentMethod("boleto")
                            //.setPaymentMethod("grabpay")
                            //.setPaymentMethod("klarna")
                            //.setPaymentMethod("sepa_debit")
                            //.setPaymentMethod("sofort")
                            .build();
            PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);
            Map<String, String> responseData = new HashMap();
            responseData.put("paymentIntent", paymentIntent.getClientSecret());
            responseData.put("ephemeralKey", ephemeralKey.getSecret());
            responseData.put("customer", customer.getId());
            //responseData.put("paymentIntent", paymentIntent.getClientSecret());
            //responseData.put("ephemeralKey", ephemeralKey.getSecret());
            //responseData.put("customer", "10L");
            responseData.put("publishableKey", "pk_test_51KoodkK4jThQYIzhmCh7GS7NTsMKoonU7QSArsxhx9YKkw0MOCd2gnNSvr5O6c0d6lbfAfuALs8UDLay8VgdbNOZ00MaGIiNoy");
            Gson gson = new Gson();
            return gson.toJson(responseData);
        }catch(Exception e ){
            e.printStackTrace();
            return null;
        }
    }

}
