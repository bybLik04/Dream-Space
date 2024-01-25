package com.example.dreamspace;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreamspace.databinding.ActivityAddBinding;
import com.example.dreamspace.databinding.ActivityHandbookBinding;

public class HandbookActivity extends BaseActivity {
    private ActivityHandbookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHandbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        binding.titleTxt.setText(title);
        binding.bodyTxt.setText(content);
        binding.backArrow.setOnClickListener(v -> {
            finish();
        });
    }
}