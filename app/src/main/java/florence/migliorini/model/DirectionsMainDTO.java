package florence.migliorini.model;

import java.util.List;
import java.util.Objects;

public class DirectionsMainDTO {
    private List<GeocodedWaypointsDTO> geocoded_waypoints;
    private List<RouteDTO> routes;
    private String status;

    public DirectionsMainDTO() {
    }

    public DirectionsMainDTO(List<GeocodedWaypointsDTO> geocoded_waypoints, List<RouteDTO> routes, String status) {
        this.geocoded_waypoints = geocoded_waypoints;
        this.routes = routes;
        this.status = status;
    }

    public List<GeocodedWaypointsDTO> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypointsDTO> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<RouteDTO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDTO> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
