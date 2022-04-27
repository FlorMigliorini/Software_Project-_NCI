package florence.migliorini.model;

import java.util.List;

public class ResultsGeoDTO {
    private List<AddressGeoDTO> results;
    private String status;

    public ResultsGeoDTO() {
    }

    public ResultsGeoDTO(List<AddressGeoDTO> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<AddressGeoDTO> getResults() {
        return results;
    }

    public void setResults(List<AddressGeoDTO> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
