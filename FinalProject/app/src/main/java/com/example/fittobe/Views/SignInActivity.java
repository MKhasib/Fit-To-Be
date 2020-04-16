package com.example.fittobe.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fittobe.Models.User;
import com.example.fittobe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.email)
    EditText mEmailEditText;
    @BindView(R.id.password)
    EditText mPasswordEditText;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.sign_in_button)
    public void signIn() {
        String mEmail = mEmailEditText.getText().toString();
        String mPassword = mPasswordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final User[] user = new User[1];
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.users)).child(mAuth.getCurrentUser().getUid());

                    mDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User temp = snapshot.getValue(User.class);
                                if(temp.getEmail().equals(mEmail)){
                                    user[0] =temp;
                                }
                            }
                            logIn(user[0]);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w(TAG, "Failed to read value.", databaseError.toException());

                        }
                    });


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), R.string.invalid, Toast.LENGTH_SHORT).show();

            }
        });
    }
    void logIn(User user){
        editor.putBoolean(getString(R.string.logged_in), true);
        editor.putString(getString(R.string.email_key), user.getEmail());
        editor.putString(getString(R.string.password_key), user.getPassword());
        editor.putBoolean(getString(R.string.signed_up_key),true);
        editor.putString(getString(R.string.gender),user.getGender());
        editor.commit();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
