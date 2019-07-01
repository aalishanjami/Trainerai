package detection.com.trainerai.Models;


import java.util.List;

import detection.com.trainerai.Models.CreateUser;

import retrofit2.Call;

import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Trxpi {

    @POST("create.php")
    Call<CreateUser> createAccount(@Body CreateUser createUser);

    //    @Headers({"Accept: application/json"})
//    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<LoginResponse> loginuser(@Body LoginResponse loginResponse);

    @POST("validate_token.php")
    Call<DataResponse> jwtget(@Body DataResponse dataResponse);

    @POST("search.php")
    Call<SearchResponse> search(@Query ("s")String s, @Body SearchResponse searchResponse);
}
