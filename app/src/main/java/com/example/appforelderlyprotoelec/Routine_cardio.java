package com.example.appforelderlyprotoelec;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Routine_cardio extends AppCompatActivity {
    View view,popupView;
    YouTubePlayerView youTubePlayerView;
    TextView title;
    Button infoButton,warmupButton,exercise1Button,exercise2Button,exercise3Button,exercise4Button,exercise5Button,cooldownButton;
    RatingBar rb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_step_by_step);
        title= (TextView)findViewById(R.id.TitleText);
        title.setText("30 Minute Cardio ");
        infoButton = (Button)findViewById(R.id.infoButton);
        warmupButton=(Button)findViewById(R.id.warmupButton);
        exercise1Button=(Button)findViewById(R.id.exercise1);
        exercise2Button=(Button)findViewById(R.id.exercise2);
        exercise3Button=(Button)findViewById(R.id.exercise3);
        exercise4Button=(Button)findViewById(R.id.exercise4);
        exercise5Button=(Button)findViewById(R.id.exercise5);
        cooldownButton=(Button)findViewById(R.id.coolDown);
        exercise1Button.setText("Side step");
        exercise2Button.setText("marching");
        exercise3Button.setText("outward punches");
        exercise4Button.setText("skaters");
        exercise5Button.setText("knee taps");
        cooldownButton.setText("CoolDown: Stretches");


        exercise1Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.popup_step_info, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("Step to the side with one foot, shift your weight onto that foot, and then bring the other foot to meet it.\nRepeat the same motion on the opposite side.");
                ImageView image = (ImageView)popupView.findViewById(R.id.exerciseImage);
                image.setImageResource(R.drawable.side_step);
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });
            }});


        exercise3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_step_info, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("Punch your right arm out in front of you with force, followed by the left arm. Repeat & increase speed as you do.");
                ImageView image = (ImageView)popupView.findViewById(R.id.exerciseImage);
                image.setImageResource(R.drawable.punching);
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });



            }
        });
        exercise4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_step_info_vidoe, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("watch the 30 second video below for a demo on how to do skaters");
                ImageView image = (ImageView)popupView.findViewById(R.id.armcircleImage);
                image.setImageResource(R.drawable.skaters);
                //Jx2KXGbQkYM

                youTubePlayerView = (YouTubePlayerView)popupView.findViewById(R.id.video_step);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = "Jx2KXGbQkYM";
                        youTubePlayer.cueVideo(videoId, 0f);
                    }
                });
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });



            }
        });

        cooldownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_step_info_vidoe, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("Click the video for a 5 minute yoga stretch and moment of mindfulness.");
                ImageView image = (ImageView)popupView.findViewById(R.id.armcircleImage);
                image.setImageResource(R.drawable.skaters);
                image.setVisibility(View.GONE);
                //Jx2KXGbQkYM

                youTubePlayerView = (YouTubePlayerView)popupView.findViewById(R.id.video_step);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = "nQFf38xeBww";
                        youTubePlayer.cueVideo(videoId, 0f);
                    }
                });
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });



            }
        });

        exercise5Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.popup_step_info, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("Raise knees to waist level in front of you so that they wake a 90 degree bend.\nTap with the palms of your hand.\nDo this as fast as you can.");
                ImageView image = (ImageView)popupView.findViewById(R.id.exerciseImage);
                image.setImageResource(R.drawable.marching);
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });
            }});
        exercise2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_step_info, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("March on the spot, raising your knees to hip level and swinging your arms wide.");
                ImageView image = (ImageView)popupView.findViewById(R.id.exerciseImage);
                image.setImageResource(R.drawable.marching);
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }

                });

            }
        });
        warmupButton.setText("Warm Up: 1 minute arm circles");
        warmupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);


                View popupView = inflater.inflate(R.layout.popup_step_info, null);
                findViewById(R.id.stepBystep).setAlpha((float) 0.7);
                //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                TextView text = popupView.findViewById(R.id.exerciseInfo);
                text.setText("Place arms out wide and circle forwards for 30 seconds, making the circles bigger as you do.\nRepeat in the opposite direction.");
                popupWindow.setElevation(50);
                popupWindow.setAnimationStyle(-1);
                popupWindow.setHeight(1500);
                popupWindow.setWidth(1010);
                popupWindow.showAtLocation(view, Gravity.CENTER,0, 0);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                    }
                });

            }
        });
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup, null);

        rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);
        rb.setRating(1f);


        infoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                popupViewCreate(view);
    }


    private void popupViewCreate(View view) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            //View popupView = inflater.inflate(R.layout.popup, null);
            findViewById(R.id.stepBystep).setAlpha((float) 0.7);
            //rb = (RatingBar) popupView.findViewById(R.id.ratingBarDifficulty);


            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            TextView text = popupView.findViewById(R.id.equipment);
            text.setText("Do each exercise for 20 seconds and take a 10 second break between them. Repeat 4 times.\n\nEquipment used: A wedge (this can be a thick book or anything else you can step on), a chair/ bench, resistance bands(not required)");
            popupWindow.setElevation(50);
            popupWindow.setAnimationStyle(-1);
            popupWindow.showAtLocation(view, Gravity.CENTER,0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    findViewById(R.id.stepBystep).setAlpha((float) 1.0);

                }
            });
        }
    });
        }
    }