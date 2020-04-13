package com.example.fittobe.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fittobe.AsyncTasks.FetchWorkOutsAsyncTask;
import com.example.fittobe.Controllers.WorkOutsAdapter;
import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private WorkOutsAdapter mWorkOutsAdapter;
    private List<Exercise> mAllExercises = new ArrayList<>();
    private List<Exercise> mFavoriteExercises = new ArrayList<>();

    Context mContext;
    SharedPreferences.Editor editor;
    SharedPreferences mSharedPreferences;
    String email_key;
    String password_key;
    String null_temp;
    String type;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        email_key = getString(R.string.email_key);
        password_key = getString(R.string.password_key);
        null_temp = getString(R.string.null_temp);
        type = mSharedPreferences.getString(getString(R.string.gender), null_temp);
        if (type.equals(getString(R.string.female))) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.color_slide_3))));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.color_slide_3_dark));
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(getString(R.string.workouts));

        mContext = getApplicationContext();
        initializeRecyclerView();
        callTheAsyncTask();
    }

    private void initializeRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mWorkOutsAdapter = new WorkOutsAdapter(mAllExercises, mContext);
        mRecyclerView.setAdapter(mWorkOutsAdapter);
        mWorkOutsAdapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(MainActivity.this, WorkOutActivity.class);
                intent.putExtra(getString(R.string.workout_name), mAllExercises.get(position).getName());
                intent.putExtra(getString(R.string.workout_id), mAllExercises.get(position).getVideoId());
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if the user is signed in then show all workouts & favorite workouts
        if (mSharedPreferences.getString(email_key, null_temp).equals(null_temp))
            getMenuInflater().inflate(R.menu.normal_settings, menu);

        else if (type.equals(getString(R.string.gender)))
            getMenuInflater().inflate(R.menu.settings, menu);
        else
            getMenuInflater().inflate(R.menu.settings_female, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                LogOut();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void callTheAsyncTask() {
        new FetchWorkOutsAsyncTask(type) {
            @Override
            protected void onPostExecute(List<Exercise> result) {
                super.onPostExecute(result);
                mAllExercises = result;
                mWorkOutsAdapter.setExercises(result);
                mWorkOutsAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void LogOut() {
        editor = mSharedPreferences.edit();
        SimpleDialog simpleDialog = new SimpleDialog(editor);
        simpleDialog.show(getSupportFragmentManager(), getString(R.string.tag));
    }
}
