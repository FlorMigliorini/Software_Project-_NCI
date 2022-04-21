package florence.migliorini.model;

import java.util.Objects;

public class OverviewPolylineDTO {
    private String points;

    public OverviewPolylineDTO() {
    }

    public OverviewPolylineDTO(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverviewPolylineDTO that = (OverviewPolylineDTO) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public String toString() {
        return "OverviewPolylineDTO{" +
                "points='" + points + '\'' +
                '}';
    }
}
