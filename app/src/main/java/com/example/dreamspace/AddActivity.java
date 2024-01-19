package com.example.dreamspace;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dreamspace.databinding.ActivityAddBinding;
import com.example.dreamspace.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private ToggleButton toggleButton;
    private RadioButton radioButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void back(View view) {
        finish();
    }

    public void save(View view) {
        String title = binding.textTitle.getText().toString().trim();
        String body = binding.textBody.getText().toString().trim();
        int selectedRadioButtonId  = binding.radioGroup.getCheckedRadioButtonId();
        int selectedToggleButtonId = binding.toggleButton.getCheckedButtonId();

        if (selectedRadioButtonId  != -1) {
            RadioButton selectedType = findViewById(selectedRadioButtonId );
            int type = Integer.parseInt(selectedType.getTag().toString());

            if (selectedToggleButtonId != -1) {
                Button selectedRate = findViewById(selectedToggleButtonId);
                int rating = Integer.parseInt(selectedRate.getTag().toString());

                if (!title.isEmpty() && !body.isEmpty()) {
                    saveDreamToFirestore(title, body, rating, type);
                } else {
                    Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Выберите оценку сна!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Выберите тип сна!", Toast.LENGTH_SHORT).show();
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

        // Добавление данных документа в Firestore
        dreamsCollectionRef.add(dreamData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddActivity.this, "Сон успешно добавлен", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddActivity.this, "Ошибка при добавлении сна", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}