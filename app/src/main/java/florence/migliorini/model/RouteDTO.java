package florence.migliorini.model;

import java.util.List;
import java.util.Objects;

public class RouteDTO {
    private BoundsDTO bounds;
    private String copyrights;
    private List<LegDTO> legs;
    private OverviewPolylineDTO overview_polyline;
    private String sumary;
    private List<Object> warnings;
    private List<Object> waypoint_order;

    public RouteDTO() {
    }

    public RouteDTO(BoundsDTO bounds, String copyrights, List<LegDTO> legs,
                    OverviewPolylineDTO overview_polyline, String sumary, List<Object> warnings, List<Object> waypoint_order) {
        this.bounds = bounds;
        this.copyrights = copyrights;
        this.legs = legs;
        this.overview_polyline = overview_polyline;
        this.sumary = sumary;
        this.warnings = warnings;
        this.waypoint_order = waypoint_order;
    }

    public BoundsDTO getBounds() {
        return bounds;
    }

    public void setBounds(BoundsDTO bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<LegDTO> getLegs() {
        return legs;
    }

    public void setLegs(List<LegDTO> legs) {
        this.legs = legs;
    }

    public OverviewPolylineDTO getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(OverviewPolylineDTO overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public List<Object> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    public List<Object> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List<Object> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteDTO routeDTO = (RouteDTO) o;
        return Objects.equals(bounds, routeDTO.bounds) && Objects.equals(copyrights, routeDTO.copyrights) && Objects.equals(legs, routeDTO.legs) && Objects.equals(overview_polyline, routeDTO.overview_polyline) && Objects.equals(sumary, routeDTO.sumary) && Objects.equals(warnings, routeDTO.warnings) && Objects.equals(waypoint_order, routeDTO.waypoint_order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bounds, copyrights, legs, overview_polyline, sumary, warnings, waypoint_order);
    }

    @Override
    public String toString() {
        return "RouteDTO{" +
                "bounds=" + bounds +
                ", copyrights='" + copyrights + '\'' +
                ", legs=" + legs +
                ", overview_polyline=" + overview_polyline +
                ", sumary='" + sumary + '\'' +
                ", warnings=" + warnings +
                ", waypoint_order=" + waypoint_order +
                '}';
    }
}
