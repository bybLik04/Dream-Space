package com.example.dreamspace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dreamspace.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String name = preferences.getString("Name", "");

        binding.usernameText.setText(name);

        updateGreeting();
        binding.addDream.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            startActivity(intent);
        });

        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<TextData> textDataList = createTextDataList(); // Замените это на вашу логику загрузки данных из файла

        HandbookAdapter handbookAdapter = new HandbookAdapter(textDataList, requireContext());
        binding.recyclerView2.setAdapter(handbookAdapter);

        return view;
    }

    private List<TextData> createTextDataList() {
        List<TextData> textDataList = new ArrayList<>();
        textDataList.add(new TextData(getString(R.string.title1), getString(R.string.body1)));
        textDataList.add(new TextData(getString(R.string.title2), getString(R.string.body2)));

        return textDataList;
    }

    private void updateGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

        if (hourOfDay >= 6 && hourOfDay < 12) {
            greeting = getString(R.string.morning);
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greeting = getString(R.string.greeting);
        } else if (hourOfDay >= 18 && hourOfDay < 24) {
            greeting = getString(R.string.evening);
        } else {
            greeting = getString(R.string.night);
        }

        binding.greeteingText.setText(greeting);
    }
}