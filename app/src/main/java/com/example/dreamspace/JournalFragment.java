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

import com.example.dreamspace.databinding.DreamItemBinding;
import com.example.dreamspace.databinding.FragmentJournalBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment implements DreamClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private DreamItemBinding binding;

    private Button add_btn, dream_btn;
    private RecyclerView recyclerView;
    private DreamAdapter dreamAdapter;
    private List<Dream> dreams;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JournalFragment newInstance(String param1, String param2) {
        JournalFragment fragment = new JournalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dreamAdapter = new DreamAdapter(dreams, this);
        recyclerView.setAdapter(dreamAdapter);
        loadDreamsFromFirestore();

        init(view);
        add_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        });

        return view;
    }

    public void init(View view) {
        add_btn = view.findViewById(R.id.add_dream2);
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
            // Открываем активити с полной информацией о сне
            Intent intent = new Intent(getActivity(), DreamDetailActivity.class);
            intent.putExtra("dream", selectedDream);
            startActivity(intent);
        }
    }
}