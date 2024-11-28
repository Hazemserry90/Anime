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


public class GameFragment extends Fragment {
    private static final String VALID_USERNAME = "Hazem@goku.com";
    private static final String VALID_PASSWORD = "Super_saiyan";


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
        view.findViewById(R.id.ingame).setOnClickListener(v -> {
            EditText usernameField = view.findViewById(R.id.username);
            EditText passwordField = view.findViewById(R.id.password);

            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            if (validateCredentials(username, password)) {
                // Navigate to the home screen on successful login
                Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_navbarFragment);
            } else {
                Toast.makeText(getContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    // Helper method to validate the entered username and password
    private boolean validateCredentials(String username, String password) {
        // Regex pattern for the specific username
        String usernameRegex = "Hazem@goku.com";
        // Exact match for the password
        String passwordRegex = "Super_saiyan";

        // Validate email and password using regex
        return username.matches(usernameRegex) && password.matches(passwordRegex);
    }
    }

