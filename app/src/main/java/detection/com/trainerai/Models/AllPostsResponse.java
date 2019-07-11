package detection.com.trainerai.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllPostsResponse {
    @SerializedName("records")
    @Expose
    private List<PostsArray> records = null;

    public List<PostsArray> getRecords() {
        return records;
    }

    public void setRecords(List<PostsArray> records) {
        this.records = records;
    }
}
