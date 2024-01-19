package com.example.dreamspace;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreamspace.databinding.ActivityDreamDetailBinding;

public class DreamDetailActivity extends BaseActivity {
    private ActivityDreamDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_detail);

        Dream dream = getIntent().getParcelableExtra("dream");

        binding.titleTextView.setText("Название: "+dream.getTitle());
        binding.bodyTextView.setText(dream.getBody());
        binding.ratingTextView.setText(String.valueOf(dream.getRating()));
        binding.typeTextView.setText(dream.getType() == 1 ? "Сон" : "Кошмар");
        binding.dateTextView.setText(dream.getDate());

    }
}