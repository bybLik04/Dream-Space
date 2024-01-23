package com.example.dreamspace;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dreamspace.databinding.ActivityLoginBinding;
import com.example.dreamspace.databinding.DialogLayoutBinding;

public class MyDialog extends Dialog implements View.OnClickListener {
    private DialogLayoutBinding binding;
    private SharedPreferences sharedPreferences;
    private DialogListener dialogListener;

    public MyDialog(Context context, SharedPreferences sharedPreferences, DialogListener listener) {
        super(context);
        this.sharedPreferences = sharedPreferences;
        this.dialogListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            saveData();
            dismiss();
            dialogListener.onDataSaved();
        }
    }

    private void saveData() {
        String name = binding.editTextName.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", name);
        editor.apply();
    }
}
