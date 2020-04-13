package com.example.fittobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences mSharedPreferences;
    String email_key;
    String password_key;
    String null_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        email_key = getString(R.string.email_key);
        password_key = getString(R.string.password_key);
        null_temp = getString(R.string.null_temp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

            //if the user is a free user ask him if wants really to leave

            editor = mSharedPreferences.edit();
            SimpleDialog simpleDialog = new SimpleDialog(editor);
            simpleDialog.show(getSupportFragmentManager(), getString(R.string.tag));

    }
}
