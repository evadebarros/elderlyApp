package com.example.appforelderlyprotoelec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] weather={"cloudy","rain","sunny"};
    String curWeather,curLocation,curTemp;
    Button cloudyButton, sunnyButton,rainyButton;
    ImageView currentWeather;
    TextView location, temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cloudyButton=findViewById(R.id.cloudyButton);
        sunnyButton=findViewById(R.id.sunnyButton);
        rainyButton=findViewById(R.id.rainyButton);
        currentWeather=(ImageView) findViewById(R.id.currentWeatherView);
        curWeather = getCurrentWeather();
        location=findViewById(R.id.location);
        temperature=findViewById(R.id.currentTemp);
        curTemp = getCurrentTemperature();
        temperature.setText(curTemp);
        curLocation= getMyCurrentLocation();
        location.setText(curLocation);
        setCurrentWeatherImage(currentWeather,curWeather);
        replaceFragment(new cloudyFragment());
        rainyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                replaceFragment(new RainyFragment());
            }
        });
        cloudyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                replaceFragment(new cloudyFragment());
            }

        });
        sunnyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                replaceFragment(new sunnyFragment());
            }
        });

    }

    private String getCurrentTemperature() {//this info will be gotten from API in future...
        curTemp="14";
        String curTempConcat=curTemp+"°C";
        return curTempConcat;
    }

    private String getMyCurrentLocation() {//this info will be gotten from API in future...
        curLocation="Galway";
        return curLocation;
    }

    private String getCurrentWeather() {//this info will be gotten from an API in future...
        curWeather=weather[1];
        return curWeather;
    }

    private void setCurrentWeatherImage(ImageView currentWeather,String curWeather) {

        if(curWeather.equalsIgnoreCase("cloudy")){
        currentWeather.setImageResource(R.drawable.cloudy);
            }

        if(curWeather.equalsIgnoreCase("rain")){
            currentWeather.setImageResource(R.drawable.rainy);
        }

        if(curWeather.equalsIgnoreCase("sunny")){
            currentWeather.setImageResource(R.drawable.sunny);
        }
        /**else {
            currentWeather.setImageResource(R.drawable.baseline_no_photography_24);//weather not available yet
        }**/

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}