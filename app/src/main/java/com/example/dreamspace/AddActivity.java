package com.example.dreamspace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreamspace.databinding.ActivityAddBinding;
import com.example.dreamspace.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends BaseActivity {
    private ActivityAddBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.rateToggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {

            if (isChecked) {
                MaterialButton checkedButton = findViewById(checkedId);

                checkedButton.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));

                for (int i = 0; i < group.getChildCount(); i++) {
                    View child = group.getChildAt(i);
                    if (child instanceof MaterialButton && child.getId() != checkedId) {
                        ((MaterialButton) child).setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                    }
                }
            }
        });

        binding.typeToggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {

            if (isChecked) {
                MaterialButton checkedButton = findViewById(checkedId);

                checkedButton.setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));

                for (int i = 0; i < group.getChildCount(); i++) {
                    View child = group.getChildAt(i);
                    if (child instanceof MaterialButton && child.getId() != checkedId) {
                        ((MaterialButton) child).setTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
                    }
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {
        String title = binding.textTitle.getText().toString().trim();
        String body = binding.textBody.getText().toString().trim();
        int selectedRateToggleId = binding.rateToggleButton.getCheckedButtonId();
        int selectedTypeToggleId  = binding.typeToggleButton.getCheckedButtonId();

        if (!title.isEmpty() && !body.isEmpty()) {
            if (selectedRateToggleId != -1) {
                Button selectedRate = findViewById(selectedRateToggleId);
                int rating = Integer.parseInt(selectedRate.getTag().toString());

                if (selectedTypeToggleId  != -1) {
                    Button selectedType = findViewById(selectedTypeToggleId);
                    int type = Integer.parseInt(selectedType.getTag().toString());
                    saveDreamToFirestore(title, body, rating, type);
                } else {
                    Toast.makeText(this, getString(R.string.type_err), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.rate_err), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,getString(R.string.fill_fields_err) , Toast.LENGTH_SHORT).show();
        }

    }

    private void saveDreamToFirestore(String title, String body, int rating, int type) {
        String userId = auth.getCurrentUser().getUid();

        DocumentReference userDocumentRef = db.collection("users").document(userId);

        CollectionReference dreamsCollectionRef = userDocumentRef.collection("dreams");

        Map<String, Object> dreamData = new HashMap<>();
        dreamData.put("title", title);
        dreamData.put("body", body);
        dreamData.put("rating", rating);
        dreamData.put("type", type);
        dreamData.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        dreamsCollectionRef.add(dreamData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddActivity.this, getString(R.string.add_dream_succes), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddActivity.this, getString(R.string.add_dream_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}