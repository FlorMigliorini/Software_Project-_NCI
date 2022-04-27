package florence.migliorini.model;

import java.util.List;

public class AddressGeoDTO {
    private List<AddressComponentGeoDTO> address_components;
    private String formatted_address;
    private GeometryGeoDTO geometry;
    private String place_id;
    private List<String> types;

    public AddressGeoDTO() {
    }

    public AddressGeoDTO(List<AddressComponentGeoDTO> address_components, String formatted_address, GeometryGeoDTO geometry, String place_id, List<String> types) {
        this.address_components = address_components;
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        this.place_id = place_id;
        this.types = types;
    }

    public List<AddressComponentGeoDTO> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponentGeoDTO> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public GeometryGeoDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoDTO geometry) {
        this.geometry = geometry;
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
}
