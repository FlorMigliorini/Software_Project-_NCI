package florence.migliorini.model;

import java.util.Objects;

public class MapDTO {
    private String text;
    private String value;

    public MapDTO() {
    }

    public MapDTO(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        MapDTO mapDTO = (MapDTO) o;
        return Objects.equals(text, mapDTO.text) && Objects.equals(value, mapDTO.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, value);
    }

    @Override
    public String toString() {
        return "MapDTO{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
