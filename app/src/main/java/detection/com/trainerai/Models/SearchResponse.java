package detection.com.trainerai.Models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("records")
    @Expose
    private List<RecordsArray> records = null;

    String s;

    public SearchResponse(String s) {
        this.s = s;
    }

    public List<RecordsArray> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsArray> records) {
        this.records = records;
    }

}