package ru.mail.aslanisl.allinone;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mail.aslanisl.allinone.api.BushPostApi;
import ru.mail.aslanisl.allinone.api.NewsApi;
import ru.mail.aslanisl.allinone.api.OpenWeatherMapApi;


public class App extends Application {

    private static BushPostApi bushPostApi;
    private static OpenWeatherMapApi openWeatherMapApi;
    private static NewsApi newsApi;
    private Retrofit retrofitBush;
    private Retrofit retrofitOpenWeatherMap;
    private Retrofit retrofitNewApi;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofitBush = new Retrofit.Builder()
                .baseUrl("http://www.umori.li/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        bushPostApi = retrofitBush.create(BushPostApi.class);

        retrofitOpenWeatherMap = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        openWeatherMapApi = retrofitOpenWeatherMap.create(OpenWeatherMapApi.class);

        retrofitNewApi = new Retrofit.Builder()
                .baseUrl(" https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        newsApi = retrofitNewApi.create(NewsApi.class);
    }

    public static BushPostApi getApiBush() {
        return bushPostApi;
    }

    public static OpenWeatherMapApi getOpenWeatherMapApi() {
        return openWeatherMapApi;
    }

    public static NewsApi getNewsApi() {
        return newsApi;
    }
}
