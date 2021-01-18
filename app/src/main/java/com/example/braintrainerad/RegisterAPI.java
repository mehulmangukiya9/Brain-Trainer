package com.example.braintrainerad;



import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/Brain/insert.php")
    public void insertUser(
            @Field("name") String name,
            @Field("gameTime") int gameTime,
            @Field("score") int score,
            Callback<Response> callback);
}
