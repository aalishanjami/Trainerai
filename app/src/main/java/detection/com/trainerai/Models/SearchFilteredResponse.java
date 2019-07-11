package detection.com.trainerai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchFilteredResponse {
    @SerializedName("records")
    @Expose
    private List<RecordsArray> records = null;

    String city, rating, clients;

    public SearchFilteredResponse(String city) {
        this.city = city;
    }

    public SearchFilteredResponse(String city, String rating) {
        this.city = city;
        this.rating = rating;
    }

    public SearchFilteredResponse(String city, String rating, String clients) {
        this.city = city;
        this.rating = rating;
        this.clients = clients;
    }

    public List<RecordsArray> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsArray> records) {
        this.records = records;
    }
}
