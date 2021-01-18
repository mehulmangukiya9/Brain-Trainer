package com.example.braintrainerad;



import com.example.braintrainerad.models.RetrofitScorecard;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RegisterAPI {

    String baseUrl = "http://192.168.2.33/";

    @FormUrlEncoded
    @POST("/Brain/insert.php")
    public void insertUser(
            @Field("name") String name,
            @Field("gameTime") int gameTime,
            @Field("score") int score,
            Callback<Response> callback);

    @GET("/Brain/showRetrofitData.php")
    Call<List<RetrofitScorecard>> getScoreCardData();

}
