package ru.mail.aslanisl.allinone.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mail.aslanisl.allinone.newsFragment.newsModel.News;

public interface NewsApi {

    @GET("/v1/sources")
    Call<News> getSource (@Query("category") String category,
                          @Query("language") String language,
                          @Query("country") String country,
                          @Query("apiKey") String apiKey);
}
