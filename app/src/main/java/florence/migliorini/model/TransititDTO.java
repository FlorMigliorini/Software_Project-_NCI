package florence.migliorini.model;

import java.sql.Time;
import java.util.Objects;

public class TransititDTO {
    private StopDTO arrival_stop;
    private TimeDTO arrival_time;
    private StopDTO departure_stop;
    private TimeDTO departure_time;
    private String headsign;
    private LineDTO line;
    private Integer num_stops;
    private String trip_short_name;

    public TransititDTO() {
    }

    public TransititDTO(StopDTO arrival_stop, TimeDTO arrival_time, StopDTO departure_stop,
                        TimeDTO departure_time, String headsign, LineDTO line, Integer num_stops, String trip_short_name) {
        this.arrival_stop = arrival_stop;
        this.arrival_time = arrival_time;
        this.departure_stop = departure_stop;
        this.departure_time = departure_time;
        this.headsign = headsign;
        this.line = line;
        this.num_stops = num_stops;
        this.trip_short_name = trip_short_name;
    }

    public StopDTO getArrival_stop() {
        return arrival_stop;
    }

    public void setArrival_stop(StopDTO arrival_stop) {
        this.arrival_stop = arrival_stop;
    }

    public TimeDTO getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(TimeDTO arrival_time) {
        this.arrival_time = arrival_time;
    }

    public StopDTO getDeparture_stop() {
        return departure_stop;
    }

    public void setDeparture_stop(StopDTO departure_stop) {
        this.departure_stop = departure_stop;
    }

    public TimeDTO getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(TimeDTO departure_time) {
        this.departure_time = departure_time;
    }

    public String getHeadsign() {
        return headsign;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

    public LineDTO getLine() {
        return line;
    }

    public void setLine(LineDTO line) {
        this.line = line;
    }

    public Integer getNum_stops() {
        return num_stops;
    }

    public void setNum_stops(Integer num_stops) {
        this.num_stops = num_stops;
    }

    public String getTrip_short_name() {
        return trip_short_name;
    }

    public void setTrip_short_name(String trip_short_name) {
        this.trip_short_name = trip_short_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransititDTO that = (TransititDTO) o;
        return Objects.equals(arrival_stop, that.arrival_stop) && Objects.equals(arrival_time, that.arrival_time) && Objects.equals(departure_stop, that.departure_stop) && Objects.equals(departure_time, that.departure_time) && Objects.equals(headsign, that.headsign) && Objects.equals(line, that.line) && Objects.equals(num_stops, that.num_stops) && Objects.equals(trip_short_name, that.trip_short_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrival_stop, arrival_time, departure_stop, departure_time, headsign, line, num_stops, trip_short_name);
    }

    @Override
    public String toString() {
        return "TransititDTO{" +
                "arrival_stop=" + arrival_stop +
                ", arrival_time=" + arrival_time +
                ", departure_stop=" + departure_stop +
                ", departure_time=" + departure_time +
                ", headsign='" + headsign + '\'' +
                ", line=" + line +
                ", num_stops=" + num_stops +
                ", trip_short_name='" + trip_short_name + '\'' +
                '}';
    }
}
