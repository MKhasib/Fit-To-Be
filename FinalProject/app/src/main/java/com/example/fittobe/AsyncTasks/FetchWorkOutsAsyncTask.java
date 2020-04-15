package com.example.fittobe.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.fittobe.Models.Exercise;
import com.example.fittobe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FetchWorkOutsAsyncTask extends AsyncTask<Void, Void, List<Exercise>> {
    private DatabaseReference mDatabaseReference;
    private List<Exercise> exercises;
    private static final String men = "men/workouts/";
    private static final String women = "women/workouts/";
    private  String default_type =men;

    public FetchWorkOutsAsyncTask(String type) {
        exercises = new ArrayList<>();
    if(type.equals("Female"))
    {
        default_type=women;
    }

    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(default_type);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Exercise exercise = snapshot.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                onPostExecute(exercises);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });
        return exercises;

    }

    protected void onPostExecute(List<Exercise> result) {
    this.exercises=result;

    }
}
