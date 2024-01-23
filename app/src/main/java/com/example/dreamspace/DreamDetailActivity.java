package com.example.dreamspace;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreamspace.databinding.ActivityDreamDetailBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class DreamDetailActivity extends BaseActivity {
    private ActivityDreamDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDreamDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Dream dream = getIntent().getParcelableExtra("dream");

        if (dream != null) {
            StringBuilder formattedRating = new StringBuilder();
            formattedRating.append(getString(R.string.dream_rate))
                    .append(" ")
                    .append(dream.getRating());

            StringBuilder formattedType = new StringBuilder();
            formattedType.append(getString(R.string.dream_type))
                    .append(" ")
                    .append(dream.getType() == 1 ? getString(R.string.type_1) : getString(R.string.type_2));

            binding.titleTextView.setText(dream.getTitle());
            binding.bodyTextView.setText(dream.getBody());
            binding.ratingTextView.setText(formattedRating.toString());
            binding.typeTextView.setText(formattedType.toString());
            binding.dateTextView.setText(dream.getDate());
        }

    }

    public void back(View view) {
        finish();
    }
}