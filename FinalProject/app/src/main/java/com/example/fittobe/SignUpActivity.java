package com.example.fittobe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fittobe.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    private static final String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String fullNameRegex = "[a-zA-Z ]+";
    private static final String characterRegex = ".*[a-zA-Z].*";
    private static final String numberRegex = ".*\\d.*";
    private static final String specialCharacterRegex = ".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*";
    @BindView(R.id.sign_up_button)
    Button mSignUpButton;



    @BindView(R.id.full_name)
    EditText mFullNameEditText;
    @BindView(R.id.email)
    EditText mEmailEditText;
    @BindView(R.id.password)
    EditText mPasswordEditText;
    @BindView(R.id.age)
    EditText mAgeEditText;
    @BindView(R.id.weight)
    EditText mWeightEditText;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.sign_up_button)
    public void signUp() {
        String mFullNameString = mFullNameEditText.getText().toString();
        if (Pattern.matches(fullNameRegex, mFullNameString.trim()) && mFullNameString.length() >= 6) {

        } else {
            Toast.makeText(getApplicationContext(), R.string.full_name_error, Toast.LENGTH_SHORT).show();
            return;
        }
        String mEmail = mEmailEditText.getText().toString().trim();
        if (mEmail.length() < 7) {

            Toast.makeText(this, R.string.email_length_error, Toast.LENGTH_LONG).show();
            return;
        } else {
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(mEmail);
            if (!matcher.matches()) {
                Toast.makeText(this, R.string.email_error, Toast.LENGTH_LONG).show();

                return;
            }

        }
        String mPassword = mPasswordEditText.getText().toString();
        if (mPassword.length() > 6) {
            if (Pattern.matches(characterRegex, mPassword)) {
                if (Pattern.matches(numberRegex, mPassword)) {
                    if (!Pattern.matches(specialCharacterRegex, mPassword)) {
                        Toast.makeText(getApplicationContext(), R.string.password_special_char_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.password_number_error, Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.password_char_error, Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.password_length_error, Toast.LENGTH_SHORT).show();
            return;
        }
        String mAge = mAgeEditText.getText().toString();
        int age = Integer.parseInt(mAge);
        String mWeight = mWeightEditText.getText().toString();
        double weight = Double.parseDouble(mWeight);
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
                    editor.putBoolean(getString(R.string.logged_in), true);
                    editor.putString(getString(R.string.email_key),mEmail);
                    editor.putString(getString(R.string.password_key),mPassword);
                    editor.putBoolean(getString(R.string.signed_up_key),true);
                    editor.commit();
                    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mDatabaseReference=mFirebaseDatabase.getReference().child("users");
                    User mUser=new User(mFullNameString,mEmail,mPassword,age,weight);
                    mDatabaseReference.push().setValue(mUser);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.email_exisits_error, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
