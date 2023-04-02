package com.example.appforelderlyprotoelec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivityStepper extends AppCompatActivity {

    TextView usernameText;
    Button calendarButton;
    Button stepCounterButton;
    Button weatherButton;
    Button settingsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);


        usernameText = findViewById(R.id.username_text);

        calendarButton = findViewById(R.id.calendar_button);
        stepCounterButton = findViewById(R.id.stepcounter_button);
        settingsButton = findViewById(R.id.settings_button);
        weatherButton = findViewById(R.id.weather_button);

        calendarButton.setOnClickListener(v -> {
            Intent intentCalender = new Intent(HomeActivityStepper.this, CalenderActivity.class);
            startActivity(intentCalender);
        });

        stepCounterButton.setOnClickListener(v -> {
            Intent intentStep = new Intent(HomeActivityStepper.this, StepperActivity.class);
            startActivity(intentStep);
        });

    }
}
