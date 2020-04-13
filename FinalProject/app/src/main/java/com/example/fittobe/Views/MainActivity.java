package com.example.fittobe.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fittobe.Controllers.WorkOutsAdapter;
import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;
import com.example.fittobe.Views.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private WorkOutsAdapter mWorkOutsAdapter;
    private ArrayList<Exercise> mAllExercises = new ArrayList<>();
    private List<Exercise> mFavoriteExercises = new ArrayList<>();

    Context mContext;
    SharedPreferences.Editor editor;
    SharedPreferences mSharedPreferences;
    String email_key;
    String password_key;
    String null_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(getString(R.string.workouts));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        email_key = getString(R.string.email_key);
        password_key = getString(R.string.password_key);
        null_temp = getString(R.string.null_temp);
        mContext=getApplicationContext();
        initializeRecyclerView();
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
//                Intent n = new Intent(context, .class);
//                Gson gson = new Gson();
//                n.putExtra(Exercise.class.getName(), gson.toJson(mExercisesAdapter.getExercises().get(position)));
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(n, bundle);
//                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //if the user is signed in then show all workouts & favorite workouts
        if(mSharedPreferences.getString(email_key,null_temp).equals(null_temp))
            getMenuInflater().inflate(R.menu.normal_settings, menu);

        else
            getMenuInflater().inflate(R.menu.settings, menu);
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

    private void LogOut() {
            editor = mSharedPreferences.edit();
            SimpleDialog simpleDialog = new SimpleDialog(editor);
            simpleDialog.show(getSupportFragmentManager(), getString(R.string.tag));
    }
}
