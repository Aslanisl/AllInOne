package ru.mail.aslanisl.allinone.weatherFragment.weatherModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private long _3h;

    public long get3h() {
        return _3h;
    }
}