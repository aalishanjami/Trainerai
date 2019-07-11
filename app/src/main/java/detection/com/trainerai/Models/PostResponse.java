package detection.com.trainerai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponse {

    @SerializedName("records")
    @Expose
    private List<PostsArray> records = null;

    String iduser;

    public PostResponse(String iduser) {
        this.iduser = iduser;
    }

    public List<PostsArray> getRecords() {
        return records;
    }

    public void setRecords(List<PostsArray> records) {
        this.records = records;
    }

}