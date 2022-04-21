package florence.migliorini.model;

import java.util.List;
import java.util.Objects;

public class LineDTO {
    private List<AgencieDTO> agencies;
    private String color;
    private String name;
    private String short_name;
    private String text_color;
    private VehicleDTO vehicle;

    public LineDTO() {
    }

    public List<AgencieDTO> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<AgencieDTO> agencies) {
        this.agencies = agencies;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public LineDTO(List<AgencieDTO> agencies, String color, String name, String short_name, String text_color, VehicleDTO vehicle) {
        this.agencies = agencies;
        this.color = color;
        this.name = name;
        this.short_name = short_name;
        this.text_color = text_color;
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineDTO lineDTO = (LineDTO) o;
        return Objects.equals(agencies, lineDTO.agencies) && Objects.equals(color, lineDTO.color) && Objects.equals(name, lineDTO.name) && Objects.equals(short_name, lineDTO.short_name) && Objects.equals(text_color, lineDTO.text_color) && Objects.equals(vehicle, lineDTO.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agencies, color, name, short_name, text_color, vehicle);
    }

    @Override
    public String toString() {
        return "LineDTO{" +
                "agencies=" + agencies +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", text_color='" + text_color + '\'' +
                ", vehicle=" + vehicle +
                '}';
    }
}
