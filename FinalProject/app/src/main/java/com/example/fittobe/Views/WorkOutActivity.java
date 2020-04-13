package com.example.fittobe.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.fittobe.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import static com.example.fittobe.R2.string.null_temp;

public class WorkOutActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerTracker tracker;
    float startTime = 0;
    String videoId;
    String workout_name;
    SharedPreferences mSharedPreferences;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String type = mSharedPreferences.getString(getString(R.string.gender), getString(R.string.null_temp));
        if (type.equals(getString(R.string.female))) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.color_slide_3))));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.color_slide_3_dark));
        }
        if (savedInstanceState != null) {
            startTime = savedInstanceState.getFloat(getString(R.string.start_time));
            videoId=savedInstanceState.getString(getString(R.string.workout_id));
            workout_name=savedInstanceState.getString(getString(R.string.workout_name));
        }else{
            Intent intent=getIntent();
            workout_name=intent.getStringExtra(getString(R.string.workout_name));
            videoId=intent.getStringExtra(getString(R.string.workout_id));
        }
        setTitle(workout_name);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        tracker = new YouTubePlayerTracker();

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, startTime);
                youTubePlayer.addListener(tracker);

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        startTime = tracker.getCurrentSecond();
        outState.putFloat(getString(R.string.start_time), startTime);
        outState.putString(getString(R.string.workout_id), videoId);
        outState.putString(getString(R.string.workout_name), workout_name);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
}
