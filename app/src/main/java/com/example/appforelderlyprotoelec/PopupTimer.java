package com.example.appforelderlyprotoelec;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;

import android.widget.Button;
import android.widget.TextView;

public class PopupTimer extends Activity{
    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;
    private boolean isTimerRunning;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_timer);
        Button button = (Button)findViewById(R.id.start_button);
        isTimerRunning=true;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTimerRunning=true;
                running = true; // add this line

                button.setBackgroundColor(getColor(R.color.purple_200));
                runTimer();
            }
        });

    }
    private void runTimer()
    {

        // Get the text view.
        final TextView timeView
                = (TextView)findViewById(
                R.id.time);

        // Creates a new Handler
        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}
