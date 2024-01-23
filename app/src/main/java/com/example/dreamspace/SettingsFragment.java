package com.example.dreamspace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dreamspace.databinding.ActivityMainBinding;
import com.example.dreamspace.databinding.FragmentSettingsBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button abt_btn, ntf_btn, logout_btn, lang_btn;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        init(view);
        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        abt_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
        lang_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LanguageActivity.class);
            startActivity(intent);
        });
        logout_btn.setOnClickListener(v -> {
            mAuth.signOut();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Uid", "");
            editor.apply();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void init(View view) {
        logout_btn = view.findViewById(R.id.logout_btn);
        abt_btn = view.findViewById(R.id.about_btn);
        lang_btn = view.findViewById(R.id.language_btn);
    }

}