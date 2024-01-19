package com.example.dreamspace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.dreamspace.databinding.ActivityLanguageBinding;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private static final String PREF_LANGUAGE_KEY = "language_preference";
    private static final String DEFAULT_LANGUAGE = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedLanguage = preferences.getString(PREF_LANGUAGE_KEY, DEFAULT_LANGUAGE);

        if (savedLanguage.equals("en")) {
            binding.langGroup.check(R.id.radio_button_en);
        } else if (savedLanguage.equals("ru")) {
            binding.langGroup.check(R.id.radio_button_ru);
        }

        binding.langGroup.setOnCheckedChangeListener((group, checkedId) -> {

            String selectedLanguage;
            if (checkedId == R.id.radio_button_en) {
                selectedLanguage = "en";
            } else if (checkedId == R.id.radio_button_ru) {
                selectedLanguage = "ru";
            } else {
                selectedLanguage = DEFAULT_LANGUAGE;
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PREF_LANGUAGE_KEY, selectedLanguage);
            editor.apply();

            setAppLocale(selectedLanguage);
            recreate();
        });
    }

    private void setAppLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}