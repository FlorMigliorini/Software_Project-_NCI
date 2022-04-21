package florence.migliorini.model;

import java.util.Objects;

public class VehicleDTO {
    private String icon;
    private String name;
    private String type;

    public VehicleDTO() {
    }

    public VehicleDTO(String icon, String name, String type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDTO that = (VehicleDTO) o;
        return Objects.equals(icon, that.icon) && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, name, type);
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
