package florence.migliorini.model;

import java.util.List;

public class AddressComponentGeoDTO {
    private String long_name;
    private String short_name;
    private List<String> types;

    public AddressComponentGeoDTO() {
    }

    public AddressComponentGeoDTO(String long_name, String short_name, List<String> types) {
        this.long_name = long_name;
        this.short_name = short_name;
        this.types = types;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
