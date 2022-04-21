package florence.migliorini.model;

import java.util.Objects;

public class CoordenationDTO {
    private String lat;
    private String lng;

    public CoordenationDTO(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public CoordenationDTO() {
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordenationDTO that = (CoordenationDTO) o;
        return Objects.equals(lat, that.lat) && Objects.equals(lng, that.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }

    @Override
    public String toString() {
        return "CoordenationDTO{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
