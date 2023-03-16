package com.example.appforelderlyprotoelec;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Exercise_routine extends AppCompatActivity {
    View view;
    YouTubePlayerView youTubePlayerView;
    Button fullscreenButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=findViewById(R.id.videoVinyasa);
        youTubePlayerView =(YouTubePlayerView) findViewById(R.id.videoVinyasa);
        setContentView(R.layout.exercise_routine);



    }


}
