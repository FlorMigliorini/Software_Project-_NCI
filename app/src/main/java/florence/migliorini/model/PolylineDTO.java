package florence.migliorini.model;

import java.util.Objects;

public class PolylineDTO {
    private String points;

    public PolylineDTO() {
    }

    public PolylineDTO(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "PolylineDTO{" +
                "points='" + points + '\'' +
                '}';
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolylineDTO that = (PolylineDTO) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
