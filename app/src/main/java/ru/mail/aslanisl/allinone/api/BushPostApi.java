package ru.mail.aslanisl.allinone.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mail.aslanisl.allinone.bashPostFragment.BushPostModel;

public interface BushPostApi {

    @GET("/api/get")
    Call<List<BushPostModel>> getData(@Query("name") String resourceName, @Query("num") int count);

    @GET("/api/random")
    Call<List<BushPostModel>> getRandomData(@Query("num") int count);
}

