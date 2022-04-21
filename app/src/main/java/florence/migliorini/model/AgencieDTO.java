package florence.migliorini.model;

import java.util.Objects;

public class AgencieDTO {
    private String name;
    private String phone;
    private String url;

    public AgencieDTO(String name, String phone, String url) {
        this.name = name;
        this.phone = phone;
        this.url = url;
    }

    public AgencieDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgencieDTO that = (AgencieDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, url);
    }

    @Override
    public String toString() {
        return "AgencieDTO{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
