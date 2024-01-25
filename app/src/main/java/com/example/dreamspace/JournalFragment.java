package com.example.dreamspace;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dreamspace.databinding.DreamItemBinding;
import com.example.dreamspace.databinding.FragmentHomeBinding;
import com.example.dreamspace.databinding.FragmentJournalBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class JournalFragment extends Fragment implements DreamClickListener {
    private DreamItemBinding binding;
    private FragmentJournalBinding bind;
    private DreamAdapter dreamAdapter;
    private List<Dream> dreams;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public JournalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        dreams = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentJournalBinding.inflate(inflater, container, false);
        View view = bind.getRoot();

        bind.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dreamAdapter = new DreamAdapter(dreams, this);
        bind.recyclerView.setAdapter(dreamAdapter);

        loadDreamsFromFirestore();

        bind.addDream2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        });
        return view;
    }

    private void loadDreamsFromFirestore() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            CollectionReference dreamsCollectionRef = db.collection("users").document(userId).collection("dreams");

            dreamsCollectionRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dreams.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Dream dream = document.toObject(Dream.class);
                        dream.setDreamId(document.getId());
                        dreams.add(dream);
                    }
                    dreamAdapter.notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    public void onDreamItemClick(Dream selectedDream) {
        if (selectedDream != null) {
            Intent intent = new Intent(getActivity(), DreamDetailActivity.class);
            intent.putExtra("dream", selectedDream);
            startActivity(intent);
        }
    }
    @Override
    public void onDreamDeleteClick(Dream selectedDream) {
        deleteDreamFromFirestore(selectedDream);
    }
    private void deleteDreamFromFirestore(Dream dream) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String dreamId = dream.getDreamId();

            DocumentReference dreamRef = db.collection("users").document(userId)
                    .collection("dreams").document(dreamId);

            dreamRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dreams.remove(dream);
                    dreamAdapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Ошибка при удалении сна", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}