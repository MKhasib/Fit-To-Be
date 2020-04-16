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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.fittobe.R2.string.null_temp;

public class WorkOutActivity extends AppCompatActivity {
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerTracker tracker;
    float startTime = 0;
    String videoId;
    String workout_name;
    SharedPreferences mSharedPreferences;
    @BindView(R.id.favorite_button)
    Button favoriteButton;
    @BindView(R.id.tool_bar_title)
    TextView titleTextView;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.finished)
    TextView finishedTextView;

    @OnClick(R.id.finished)
    public void finishedWorkout() {
        Toast.makeText(this,getString(R.string.good),Toast.LENGTH_LONG).show();
        finish();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        String type = mSharedPreferences.getString(getString(R.string.gender), getString(R.string.null_temp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow_icon_drawable);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (type.equals(getString(R.string.female))) {
            toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.color_slide_3))));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_slide_3_dark));
        }
        if (mSharedPreferences.getString(getString(R.string.email_key), getString(R.string.null_temp)).equals(getString(R.string.null_temp))) {
            favoriteButton.setVisibility(View.GONE);
        }
        if (savedInstanceState != null) {
            startTime = savedInstanceState.getFloat(getString(R.string.start_time));
            videoId = savedInstanceState.getString(getString(R.string.workout_id));
            workout_name = savedInstanceState.getString(getString(R.string.workout_name));
        } else {
            Intent intent = getIntent();
            workout_name = intent.getStringExtra(getString(R.string.workout_name));
            videoId = intent.getStringExtra(getString(R.string.workout_id));
        }
        titleTextView.setText(workout_name);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
