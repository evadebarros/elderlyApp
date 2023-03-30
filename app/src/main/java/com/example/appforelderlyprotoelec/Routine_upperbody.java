package com.example.appforelderlyprotoelec;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Routine_upperbody extends AppCompatActivity {
    View view,popupView;
    YouTubePlayerView youTubePlayerView;
    Button infoButton;
    RatingBar rb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_upperbody);
        view = findViewById(R.id.videoUpperBody);

        infoButton = (Button)findViewById(R.id.infoButton);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup, null);

        rb = popupView.findViewById(R.id.ratingBarDifficulty);

        rb.setRating(2f);
        rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

        rb.setRating(2f);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoUpperBody);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "K9s8bRd4Fxw";
                youTubePlayer.cueVideo(videoId, 0f);
            }
        });
        infoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                //View popupView = inflater.inflate(R.layout.popup, null);
                findViewById(R.id.upper_bodyPage).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.equipment);
                text.setText("Equipment used: 1 set of dumbbells of your choosing (1kg-8kg)\n\n" +
                        "Exercises covered: Dumbbell Curls\n" +
                        "Tricep Dips\n" +
                        "Bent Over Row\n" +
                        "Press Up\n" +
                        "Straight Leg Crunch\n" +
                        "Seated Shoulder Press");
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.showAtLocation(view, Gravity.CENTER,0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.upper_bodyPage).setAlpha((float) 1.0);

                    }
                });
            }
        });
    }



}