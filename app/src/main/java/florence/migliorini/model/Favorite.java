package florence.migliorini.model;

public class Favorite {
    private int id;
    private String location;
    private String destination;

    public Favorite(){}

    public Favorite(int id, String location, String destination) {
        this.id = id;
        this.location = location;
        this.destination = destination;
    }
    public Favorite(String location, String destination) {
        this.location = location;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
