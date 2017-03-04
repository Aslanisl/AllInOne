package ru.mail.aslanisl.allinone.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mail.aslanisl.allinone.weatherFragment.weatherModel.Weather;

public interface OpenWeatherMapApi {

    @GET("/data/2.5/weather")
    Call<Weather> getOpenWeatherByCity(@Query("q") String cityName,
                                       @Query("units") String metric,
                                       @Query("appid") String apiKey);

    @GET("/data/2.5/weather")
    Call<Weather> getOpenWeatherByCoord(@Query("lat") double latitude,
                                        @Query("lon") double longitude,
                                        @Query("units") String metric,
                                        @Query("appid") String apiKey);

}
