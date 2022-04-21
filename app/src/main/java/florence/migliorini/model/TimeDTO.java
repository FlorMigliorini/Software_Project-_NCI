package florence.migliorini.model;

import java.util.Objects;

public class TimeDTO {
    private String text;
    private String time_zone;
    private String value;

    public TimeDTO() {
    }

    public TimeDTO(String text, String time_zone, String value) {
        this.text = text;
        this.time_zone = time_zone;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDTO timeDTO = (TimeDTO) o;
        return Objects.equals(text, timeDTO.text) && Objects.equals(time_zone, timeDTO.time_zone) && Objects.equals(value, timeDTO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, time_zone, value);
    }

    @Override
    public String toString() {
        return "TimeDTO{" +
                "text='" + text + '\'' +
                ", time_zone='" + time_zone + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
