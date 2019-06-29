package detection.com.trainerai.Models;

public class DataResponse {

    String jwt;
    Data data;

    public DataResponse(String jwt) {
        this.jwt = jwt;
    }

    public Data getData() {
        return data;
    }
}
