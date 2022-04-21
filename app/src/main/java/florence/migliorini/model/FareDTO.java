package florence.migliorini.model;

import java.util.Objects;

public class FareDTO {
    private String currency;
    private String text;
    private Double value;

    public FareDTO() {
    }

    public FareDTO(String currency, String text, Double value) {
        this.currency = currency;
        this.text = text;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FareDTO fareDTO = (FareDTO) o;
        return Objects.equals(currency, fareDTO.currency) && Objects.equals(text, fareDTO.text) && Objects.equals(value, fareDTO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, text, value);
    }

    @Override
    public String toString() {
        return "FareDTO{" +
                "currency='" + currency + '\'' +
                ", text='" + text + '\'' +
                ", value=" + value +
                '}';
    }
}
