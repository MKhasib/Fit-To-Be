package com.example.fittobe.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fittobe.Models.Exercise;
import com.example.fittobe.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FetchFavoriteWorkOutsAsyncTask extends AsyncTask<Void, Void, List<Exercise>> {
    private DatabaseReference mDatabaseReference;
    private List<Exercise> exercises;
    private String default_type = "users/";
    private String email;

    public FetchFavoriteWorkOutsAsyncTask(String email) {
        this.email = email;

    }

    @Override
    protected List<Exercise> doInBackground(Void... voids) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(default_type).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Exercise> exercises = new ArrayList<>();
                Exercise exercise = (Exercise) dataSnapshot.getValue(Exercise.class);
                exercises.add(exercise);
                onPostExecute(exercises);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });
        return exercises;

    }


}
