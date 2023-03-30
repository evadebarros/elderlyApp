package com.example.appforelderlyprotoelec;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Exercise_routine extends AppCompatActivity {
    View view,popupView;
    YouTubePlayerView youTubePlayerView;
    Button infoButton;
    RatingBar rb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_routine);
        //view = findViewById(R.id.videoVinyasa);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoVinyasa);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "P8uHMMmWMHQ";
                youTubePlayer.cueVideo(videoId, 0f);
            }
        });

        infoButton = (Button)findViewById(R.id.infoButton);
        infoButton = (Button)findViewById(R.id.infoButton);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup, null);
        rb = popupView.findViewById(R.id.ratingBarDifficulty);

        rb.setRating(1f);
        infoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                findViewById(R.id.exercisepage).setAlpha((float) 0.7);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.showAtLocation(view, Gravity.CENTER,0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.exercisepage).setAlpha((float) 1.0);

                    }
                });
            }
        });
    }



}