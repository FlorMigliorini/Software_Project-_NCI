package florence.migliorini.model;

import java.time.LocalDate;
import java.util.Date;

public class TravelDTO {
    private Integer id;
    private String location;
    private String destiny;
    private LocalDate dtInitial;
    private Integer cdTransport;
    private String duration;
    private Integer value;
    private String dtDuration;
    private String dsTitleTicket;
    private String dtHourDeparture;
    private String dtHourTravel;
    private Integer numPassengers;

    public TravelDTO(Integer id, String location, String destiny,
                     LocalDate dtInitial, Integer cdTransport,
                     String duration, Integer value, String dtDuration,
                     String dsTitleTicket, String dtHourDeparture,
                     String dtHourTravel, Integer numPassengers) {
        this.id = id;
        this.location = location;
        this.destiny = destiny;
        this.dtInitial = dtInitial;
        this.cdTransport = cdTransport;
        this.duration = duration;
        this.value = value;
        this.dtDuration = dtDuration;
        this.dsTitleTicket = dsTitleTicket;
        this.dtHourDeparture = dtHourDeparture;
        this.dtHourTravel = dtHourTravel;
        this.numPassengers = numPassengers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public LocalDate getDtInitial() {
        return dtInitial;
    }

    public void setDtInitial(LocalDate dtInitial) {
        this.dtInitial = dtInitial;
    }

    public Integer getCdTransport() {
        return cdTransport;
    }

    public void setCdTransport(Integer cdTransport) {
        this.cdTransport = cdTransport;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDtDuration() {
        return dtDuration;
    }

    public void setDtDuration(String dtDuration) {
        this.dtDuration = dtDuration;
    }

    public String getDsTitleTicket() {
        return dsTitleTicket;
    }

    public void setDsTitleTicket(String dsTitleTicket) {
        this.dsTitleTicket = dsTitleTicket;
    }

    public String getDtHourDeparture() {
        return dtHourDeparture;
    }

    public void setDtHourDeparture(String dtHourDeparture) {
        this.dtHourDeparture = dtHourDeparture;
    }

    public String getDtHourTravel() {
        return dtHourTravel;
    }

    public void setDtHourTravel(String dtHourTravel) {
        this.dtHourTravel = dtHourTravel;
    }

    public Integer getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(Integer numPassengers) {
        this.numPassengers = numPassengers;
    }
}
