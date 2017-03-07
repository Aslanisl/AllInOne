package ru.mail.aslanisl.allinone.weatherFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mail.aslanisl.allinone.App;
import ru.mail.aslanisl.allinone.R;
import ru.mail.aslanisl.allinone.weatherFragment.weatherModel.Weather;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.widget.Toast.makeText;

public class WeatherFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = WeatherFragment.class.getSimpleName();
    public static final String API_WEATHER_KEY = "3e4410ee9a25ce9ee054c811d9c79a71";
    public static final String API_WEATHER_METRIC = "metric";

    ImageView mWeatherIcon;
    TextView mTempMin;
    TextView mTempMax;
    TextView mCityName;
    TextView mTempCurrent;

    Spinner mCitySpinner;
    Button mOkSpinnerButton;

    TextView mLongitude;
    TextView mLatitude;
    Button mGpsButton;

    String mCitySelected;

    Call<Weather> mCallWeatherByCity;
    Call<Weather> mCallWeatherByCoord;

    MapView mMapView;
    private GoogleMap googleMap;

    LocationService mLocationService;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        //Updating weather by spinner
        mCitySpinner = (Spinner) rootView.findViewById(R.id.city_spinner);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getContext(), R.array.cities_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCitySpinner.setAdapter(adapter);
        mCitySpinner.setOnItemSelectedListener(this);
        mOkSpinnerButton = (Button) rootView.findViewById(R.id.weather_button);
        mOkSpinnerButton.setOnClickListener(this);

        //Display the weather
        mWeatherIcon = (ImageView) rootView.findViewById(R.id.weather_icon);
        mTempMin = (TextView) rootView.findViewById(R.id.temp_min);
        mTempMax = (TextView) rootView.findViewById(R.id.temp_max);
        mCityName = (TextView) rootView.findViewById(R.id.city_name);
        mTempCurrent = (TextView) rootView.findViewById(R.id.current_temp);

        //Update the weather by GPS or Network
        mLongitude = (TextView) rootView.findViewById(R.id.textView_longitude);
        mLatitude = (TextView) rootView.findViewById(R.id.textView_latitude);
        mGpsButton = (Button) rootView.findViewById(R.id.button_gps);
        mGpsButton.setOnClickListener(this);

        //Update weather from Moscow
        mCitySelected = "Moscow";
        updateWeatherByCity(mCitySelected);

        //Get the location
        mLocationService = new LocationService(getActivity());
        mLocationService.getLocation(); // Request location (with permission checking)

        //Init map service
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });


        return rootView;
    }

    public void updateWeatherByCity(String city) {

        mCallWeatherByCity = App.getOpenWeatherMapApi().getOpenWeatherByCity(city, API_WEATHER_METRIC, API_WEATHER_KEY);

        mCallWeatherByCity.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful()) {
                    mCityName.setText(response.body().getName());

                    mTempMin.setText(String.format(Locale.ENGLISH, "%.1f °C",
                            response.body().getMain().getTempMin()));
                    mTempMax.setText(String.format(Locale.ENGLISH, "%.1f °C",
                            response.body().getMain().getTempMax()));
                    mTempCurrent.setText(String.format(Locale.ENGLISH, "%.1f °C",
                            response.body().getMain().getTemp()));

                    Picasso.with(getActivity()).load("http://openweathermap.org/img/w/"
                            + response.body().getWeather().get(0).getIcon() + ".png").into(mWeatherIcon);
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWeatherByLocation(double longitude, double latitude) {

        mCallWeatherByCoord = App.getOpenWeatherMapApi()
                .getOpenWeatherByCoord(latitude, longitude, API_WEATHER_METRIC, API_WEATHER_KEY);

        if (!mCallWeatherByCoord.isExecuted()) {
            mCallWeatherByCoord.enqueue(new Callback<Weather>() {
                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response != null) {
                        mCityName.setText(response.body().getName());

                        mTempMin.setText(String.format(Locale.ENGLISH, "%.1f °C",
                                response.body().getMain().getTempMin()));
                        mTempMax.setText(String.format(Locale.ENGLISH, "%.1f °C",
                                response.body().getMain().getTempMax()));
                        mTempCurrent.setText(String.format(Locale.ENGLISH, "%.1f °C",
                                response.body().getMain().getTemp()));

                        Picasso.with(getActivity()).load("http://openweathermap.org/img/w/"
                                + response.body().getWeather().get(0).getIcon() + ".png").into(mWeatherIcon);
                    } else {
                        Toast.makeText(getActivity(), "Нет данных о погоде", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {
                    Log.d("TAG", t.toString());
                }
            });
        } else Toast.makeText(getActivity(), "Погода уже загружена", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mCallWeatherByCity != null)
        mCallWeatherByCity.cancel();
        else if (mCallWeatherByCoord != null)
        mCallWeatherByCoord.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationService.getLoc();
            } else {
                // TODO NO PERMISSION
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
        mLocationService.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_gps:
                mLongitude.setText(String.format(Locale.ENGLISH, "%.4f", mLocationService.getmLocation().getLongitude()));
                mLatitude.setText(String.format(Locale.ENGLISH, "%.4f", mLocationService.getmLocation().getLatitude()));
                updateWeatherByLocation(mLocationService.getmLocation().getLongitude(),
                        mLocationService.getmLocation().getLatitude());
                break;
            case R.id.weather_button:
                updateWeatherByCity(mCitySelected);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCitySelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
