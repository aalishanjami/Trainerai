package detection.com.trainerai.Models;

import retrofit2.Call;

import retrofit2.http.Body;

import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Trxpi {

    @POST("create.php")
    Call<CreateUser> createAccount(@Body CreateUser createUser);

    @POST("login.php")
    Call<LoginResponse> loginuser(@Body LoginResponse loginResponse);

    @POST("validate_token.php")
    Call<DataResponse> jwtget(@Body DataResponse dataResponse);

    @POST("search.php")
    Call<SearchResponse> search(@Query("s") String s, @Body SearchResponse searchResponse);

    @POST("read_one.php")
    Call<SearchProfileResponse> searchprofile(@Query("id") String id, @Body SearchProfileResponse searchProfileResponse);

    @POST("search.php")
    Call<PostResponse> postsuser(@Query("s") String iduser, @Body PostResponse postResponse);

    @POST("create.php")
    Call<CreatePost> createpostnew(@Body CreatePost createPost);

    @POST("read.php")
    Call<AllPostsResponse> allposts(@Body AllPostsResponse allPostsResponse);
}
