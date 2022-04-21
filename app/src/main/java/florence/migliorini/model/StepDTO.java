package florence.migliorini.model;

import java.util.Objects;

public class StepDTO {
    private MapDTO distance;
    private MapDTO duration;
    private CoordenationDTO end_location;
    private String html_instructions;
    private PolylineDTO polyline;
    private CoordenationDTO start_location;
    private TransititDTO transit_details;
    private String travel_mode;

    public StepDTO() {
    }

    public StepDTO(MapDTO distance, MapDTO duration, CoordenationDTO end_location, String html_instructions,
                   PolylineDTO polyline, CoordenationDTO start_location, TransititDTO transit_details, String travel_mode) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.html_instructions = html_instructions;
        this.polyline = polyline;
        this.start_location = start_location;
        this.transit_details = transit_details;
        this.travel_mode = travel_mode;
    }

    public MapDTO getDistance() {
        return distance;
    }

    public void setDistance(MapDTO distance) {
        this.distance = distance;
    }

    public MapDTO getDuration() {
        return duration;
    }

    public void setDuration(MapDTO duration) {
        this.duration = duration;
    }

    public CoordenationDTO getEnd_location() {
        return end_location;
    }

    public void setEnd_location(CoordenationDTO end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public PolylineDTO getPolyline() {
        return polyline;
    }

    public void setPolyline(PolylineDTO polyline) {
        this.polyline = polyline;
    }

    public CoordenationDTO getStart_location() {
        return start_location;
    }

    public void setStart_location(CoordenationDTO start_location) {
        this.start_location = start_location;
    }

    public TransititDTO getTransit_details() {
        return transit_details;
    }

    public void setTransit_details(TransititDTO transit_details) {
        this.transit_details = transit_details;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepDTO stepDTO = (StepDTO) o;
        return Objects.equals(distance, stepDTO.distance) && Objects.equals(duration, stepDTO.duration) && Objects.equals(end_location, stepDTO.end_location) && Objects.equals(html_instructions, stepDTO.html_instructions) && Objects.equals(polyline, stepDTO.polyline) && Objects.equals(start_location, stepDTO.start_location) && Objects.equals(transit_details, stepDTO.transit_details) && Objects.equals(travel_mode, stepDTO.travel_mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, duration, end_location, html_instructions, polyline, start_location, transit_details, travel_mode);
    }

    @Override
    public String toString() {
        return "StepDTO{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", end_location=" + end_location +
                ", html_instructions='" + html_instructions + '\'' +
                ", polyline=" + polyline +
                ", start_location=" + start_location +
                ", transit_details=" + transit_details +
                ", travel_mode='" + travel_mode + '\'' +
                '}';
    }
}
