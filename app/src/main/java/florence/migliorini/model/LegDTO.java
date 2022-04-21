package florence.migliorini.model;

import java.util.List;
import java.util.Objects;

public class LegDTO {
    private TimeDTO arrivalDTO;
    private TimeDTO departure_time;
    private MapDTO distance;
    private MapDTO duration;
    private String end_address;
    private CoordenationDTO end_location;
    private String start_address;
    private CoordenationDTO start_location;
    private List<StepDTO> steps;
    private List<Object> traffic_speed_entry;
    private List<Object> via_waypoint;

    public LegDTO() {
    }

    public LegDTO(TimeDTO arrivalDTO, TimeDTO departure_time, MapDTO distance, MapDTO duration, String end_address, CoordenationDTO end_location, String start_address,
                  CoordenationDTO start_location, List<StepDTO> steps, List<Object> traffic_speed_entry, List<Object> via_waypoint) {
        this.arrivalDTO = arrivalDTO;
        this.departure_time = departure_time;
        this.distance = distance;
        this.duration = duration;
        this.end_address = end_address;
        this.end_location = end_location;
        this.start_address = start_address;
        this.start_location = start_location;
        this.steps = steps;
        this.traffic_speed_entry = traffic_speed_entry;
        this.via_waypoint = via_waypoint;
    }

    public TimeDTO getArrivalDTO() {
        return arrivalDTO;
    }

    public void setArrivalDTO(TimeDTO arrivalDTO) {
        this.arrivalDTO = arrivalDTO;
    }

    public TimeDTO getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(TimeDTO departure_time) {
        this.departure_time = departure_time;
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

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public CoordenationDTO getEnd_location() {
        return end_location;
    }

    public void setEnd_location(CoordenationDTO end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public CoordenationDTO getStart_location() {
        return start_location;
    }

    public void setStart_location(CoordenationDTO start_location) {
        this.start_location = start_location;
    }

    public List<StepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDTO> steps) {
        this.steps = steps;
    }

    public List<Object> getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    public void setTraffic_speed_entry(List<Object> traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    public List<Object> getVia_waypoint() {
        return via_waypoint;
    }

    public void setVia_waypoint(List<Object> via_waypoint) {
        this.via_waypoint = via_waypoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegDTO legDTO = (LegDTO) o;
        return Objects.equals(arrivalDTO, legDTO.arrivalDTO) && Objects.equals(departure_time, legDTO.departure_time) && Objects.equals(distance, legDTO.distance) && Objects.equals(duration, legDTO.duration) && Objects.equals(end_address, legDTO.end_address) && Objects.equals(end_location, legDTO.end_location) && Objects.equals(start_address, legDTO.start_address) && Objects.equals(start_location, legDTO.start_location) && Objects.equals(steps, legDTO.steps) && Objects.equals(traffic_speed_entry, legDTO.traffic_speed_entry) && Objects.equals(via_waypoint, legDTO.via_waypoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivalDTO, departure_time, distance, duration, end_address, end_location, start_address, start_location, steps, traffic_speed_entry, via_waypoint);
    }

    @Override
    public String toString() {
        return "LegDTO{" +
                "arrivalDTO=" + arrivalDTO +
                ", departure_time=" + departure_time +
                ", distance=" + distance +
                ", duration=" + duration +
                ", end_address='" + end_address + '\'' +
                ", end_location=" + end_location +
                ", start_address='" + start_address + '\'' +
                ", start_location=" + start_location +
                ", steps=" + steps +
                ", traffic_speed_entry=" + traffic_speed_entry +
                ", via_waypoint=" + via_waypoint +
                '}';
    }
}
