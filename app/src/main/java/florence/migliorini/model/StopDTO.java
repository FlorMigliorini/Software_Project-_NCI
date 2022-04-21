package florence.migliorini.model;

import java.util.Objects;

public class StopDTO {
    private  CoordenationDTO location;
    private String name;

    public StopDTO() {
    }

    @Override
    public String toString() {
        return "StopDTO{" +
                "location=" + location +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopDTO stopDTO = (StopDTO) o;
        return Objects.equals(location, stopDTO.location) && Objects.equals(name, stopDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, name);
    }

    public CoordenationDTO getLocation() {
        return location;
    }

    public void setLocation(CoordenationDTO location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StopDTO(CoordenationDTO location, String name) {
        this.location = location;
        this.name = name;
    }
}
