package com.example.fittobe.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fittobe.R;

public class GenderDialog extends AppCompatDialogFragment {
    String null_temp;
    SharedPreferences.Editor mEditor;
    public GenderDialog(SharedPreferences.Editor editor) {
        this.mEditor=editor;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new    AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.choose_gender))
        .setPositiveButton(getString(R.string.gender), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                null_temp = getString(R.string.null_temp);
                mEditor.putBoolean(getString(R.string.logged_in), true);
                mEditor.putString(getString(R.string.gender),getString(R.string.gender));
                mEditor.commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton(getString(R.string.female), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEditor.putBoolean(getString(R.string.logged_in), true);
                mEditor.putString(getString(R.string.gender),getString(R.string.female));
                mEditor.commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
return builder.create();
    }
}
