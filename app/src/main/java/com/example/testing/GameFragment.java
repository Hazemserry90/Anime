package com.example.testing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.testing.R;

import com.google.firebase.auth.FirebaseAuth;


public class GameFragment extends Fragment {

    private FirebaseAuth auth;

    public GameFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout file (fragment_game) and get a reference to the root view
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        // Find the button (or clickable view) with ID `ingame` and set a click listener on it
        auth = FirebaseAuth.getInstance();

        view.findViewById(R.id.ingame).setOnClickListener(v -> {
            EditText usernameField = view.findViewById(R.id.username); // Email input field
            EditText passwordField = view.findViewById(R.id.password); // Password input field

            String email = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password, view);
        });

        return view;
    }

    // Helper method to log in a user using Firebase Authentication
    private void loginUser(String email, String password, View view) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Navigate to the home screen on successful login
                        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_navbarFragment);
                    } else {
                        // Show error message on failed login
                        Toast.makeText(getContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    }

