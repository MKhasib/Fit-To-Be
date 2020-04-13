package com.example.fittobe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SimpleDialog extends AppCompatDialogFragment {
    String email_key;
    String password_key;
    String null_temp;
    SharedPreferences.Editor mEditor;
    public SimpleDialog(SharedPreferences.Editor editor) {
        this.mEditor=editor;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new    AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.check_me))
        .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email_key = getString(R.string.email_key);
                password_key = getString(R.string.password_key);
                null_temp = getString(R.string.null_temp);
                mEditor.putBoolean(getString(R.string.logged_in), false);
                mEditor.putString(email_key, null_temp);
                mEditor.putString(password_key, null_temp);
                mEditor.commit();
                startActivity(new Intent(getActivity(), StartUpActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
return builder.create();
    }
}
