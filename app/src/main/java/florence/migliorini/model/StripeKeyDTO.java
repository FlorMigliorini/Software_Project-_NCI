package florence.migliorini.model;

public class StripeKeyDTO {
    private String customer;
    private String ephemeralKey;
    private String paymentIntent;
    private String publishableKey;

    public StripeKeyDTO(){}

    public StripeKeyDTO(String customer, String ephemeralKey, String paymentIntent, String publishableKey) {
        this.customer = customer;
        this.ephemeralKey = ephemeralKey;
        this.paymentIntent = paymentIntent;
        this.publishableKey = publishableKey;
    }

    public String getPaymentIntentClientSecret() {
        return paymentIntent;
    }

    public void setPaymentIntentClientSecret(String paymentIntentClientSecret) {
        this.paymentIntent = paymentIntentClientSecret;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEphemeralKey() {
        return ephemeralKey;
    }

    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

}
