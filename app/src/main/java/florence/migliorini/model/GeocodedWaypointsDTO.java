package florence.migliorini.model;

import java.util.List;
import java.util.Objects;

public class GeocodedWaypointsDTO {
    private String geocoder_status;
    private String place_id;
    private List<String> types;

    public GeocodedWaypointsDTO() {

    }

    public GeocodedWaypointsDTO(String geocoder_status, String place_id, List<String> types) {
        this.geocoder_status = geocoder_status;
        this.place_id = place_id;
        this.types = types;
    }

    public String getGeocoder_status() {
        return geocoder_status;
    }

    public void setGeocoder_status(String geocoder_status) {
        this.geocoder_status = geocoder_status;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeocodedWaypointsDTO that = (GeocodedWaypointsDTO) o;
        return Objects.equals(geocoder_status, that.geocoder_status) && Objects.equals(place_id, that.place_id) && Objects.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(geocoder_status, place_id, types);
    }

    @Override
    public String toString() {
        return "GeocodedWaypointsDTO{" +
                "geocoder_status='" + geocoder_status + '\'' +
                ", place_id='" + place_id + '\'' +
                ", types=" + types +
                '}';
    }
}
