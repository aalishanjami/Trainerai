package detection.com.trainerai.Models;


import detection.com.trainerai.Models.CreateUser;

import retrofit2.Call;

import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface Trxpi {

    @POST("create.php")
    Call<CreateUser> createAccount(@Body CreateUser createUser);

//    @Headers({"Accept: application/json"})
//    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<LoginResponse> loginuser(@Body LoginResponse loginResponse);
}
