package florence.migliorini.model;

import java.util.Objects;

public class BoundsDTO {
    private CoordenationDTO northeast;
    private CoordenationDTO southwest;

    public BoundsDTO() {
    }

    public BoundsDTO(CoordenationDTO northeast, CoordenationDTO southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public CoordenationDTO getNortheast() {
        return northeast;
    }

    public void setNortheast(CoordenationDTO northeast) {
        this.northeast = northeast;
    }

    public CoordenationDTO getSouthwest() {
        return southwest;
    }

    public void setSouthwest(CoordenationDTO southwest) {
        this.southwest = southwest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundsDTO boundsDTO = (BoundsDTO) o;
        return Objects.equals(northeast, boundsDTO.northeast) && Objects.equals(southwest, boundsDTO.southwest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(northeast, southwest);
    }

    @Override
    public String toString() {
        return "BoundsDTO{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }
}
