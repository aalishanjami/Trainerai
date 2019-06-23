package detection.com.trainerai;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Trxpi {

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseBody> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("dob") String dob,
            @Field("height") String height,
            @Field("weight") String weight,
            @Field("pic") String pic
    );
}
