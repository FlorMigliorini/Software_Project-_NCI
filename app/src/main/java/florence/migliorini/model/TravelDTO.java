package florence.migliorini.model;

import java.util.Date;

public class TravelDTO {
    private Integer id;
    private String location;
    private String destiny;
    private Date dtInitial;
    private Integer cdTransport;
    private String duration;
    private String value;

    public TravelDTO(Integer id, String location, String destiny, Date dtInitial, Integer cdTransport, String duration, String value) {
        this.id = id;
        this.location = location;
        this.destiny = destiny;
        this.dtInitial = dtInitial;
        this.cdTransport = cdTransport;
        this.duration = duration;
        this.value = value;
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

    public Date getDtInitial() {
        return dtInitial;
    }

    public void setDtInitial(Date dtInitial) {
        this.dtInitial = dtInitial;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCdTransport() {
        return cdTransport;
    }

    public void setCdTransport(Integer cdTransport) {
        this.cdTransport = cdTransport;
    }
}
