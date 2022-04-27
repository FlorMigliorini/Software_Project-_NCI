package florence.migliorini.model;

public class GeometryGeoDTO {
    private BoundsDTO bounds;
    private CoordenationDoubleDTO location;
    private String location_type;
    private BoundsDTO viewport;

    public GeometryGeoDTO() {
    }

    public GeometryGeoDTO(BoundsDTO bounds, CoordenationDoubleDTO location, String location_type, BoundsDTO viewport) {
        this.bounds = bounds;
        this.location = location;
        this.location_type = location_type;
        this.viewport = viewport;
    }

    public BoundsDTO getBounds() {
        return bounds;
    }

    public void setBounds(BoundsDTO bounds) {
        this.bounds = bounds;
    }

    public CoordenationDoubleDTO getLocation() {
        return location;
    }

    public void setLocation(CoordenationDoubleDTO location) {
        this.location = location;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public BoundsDTO getViewport() {
        return viewport;
    }

    public void setViewport(BoundsDTO viewport) {
        this.viewport = viewport;
    }
}
