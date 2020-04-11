package com.example.fittobe;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class StartUpActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    boolean previouslySignedUp;
    boolean enterMainActivity;
    @BindView(R.id.go_to_sign_up_button)
     Button mGoToSignUpButton;
    @OnClick(R.id.go_to_sign_up_button)
    public void loadSignUp(){
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }
    @BindView(R.id.go_to_sign_in_button)
     Button mGoToSignInButton;
    @OnClick(R.id.go_to_sign_in_button)
    public void loadSignIn(){
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
    @BindView(R.id.try_free_button)
     Button mTryFreeButton;
    @OnClick(R.id.try_free_button)
    public void loadTryFree(){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(getString(R.string.open_main_activity_free), Boolean.TRUE);
        edit.commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        enterMainActivity=mSharedPreferences.getBoolean(getString(R.string.open_main_activity_free), false);
        if(enterMainActivity)
        {
            loadTryFree();
        }
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previouslySignedUp = mSharedPreferences.getBoolean(getString(R.string.signed_up_key), false);
        if (previouslySignedUp) {
        mGoToSignInButton.setVisibility(View.VISIBLE);
        mTryFreeButton.setVisibility(View.INVISIBLE);
        }
    }
}
