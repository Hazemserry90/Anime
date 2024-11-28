package com.example.testing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EndgameFragment extends Fragment {


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endgame, container, false);

    }
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
            // Call the superclass method to ensure proper initialization
            super.onViewCreated(view, savedInstanceState);
            EditText usernameField = view.findViewById(R.id.username); // Input field for username
            EditText emailField = view.findViewById(R.id.email); // Input field for email
            EditText passwordField = view.findViewById(R.id.password); // Input field for password
            // Obtain the NavController for navigation between fragments
            final NavController navController = Navigation.findNavController(view);
            // Initialize the "Sign Up" button
            Button button = view.findViewById(R.id.signup);
            // Set an OnClickListener for the button to handle user interactions
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Retrieve user inputs from the text fields and trim leading/trailing spaces
                    String username = usernameField.getText().toString().trim();
                    String email = emailField.getText().toString().trim();
                    String password = passwordField.getText().toString().trim();
                    // Validate user inputs using a helper method
                    if (validateInputs(username, email, password)) {
                        // Navigate to the startFragment if inputs are valid
                       navController.navigate(R.id.action_endgameFragment_to_startFragment);
                    }
                }
            });

        }
    private boolean validateInputs(String name, String email, String password) {
        // Regex for name (only letters, spaces allowed, at least 2 characters)
        String usernameRegex = "^[A-Za-z\\s]{2,}$";

        // Regex for email (basic email format)
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Regex for password (at least 6 characters, at least one letter and one number)
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
// Validate the username
        if (!name.matches(usernameRegex)) {
            Toast.makeText(getContext(), "Name must contain only letters and spaces, with at least 2 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
// Validate the email
        if (!email.matches(emailRegex)) {
            Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
// Validate the password
        if (!password.matches(passwordRegex)) {
            Toast.makeText(getContext(), "Password must be at least 6 characters long and include at least one letter and one number", Toast.LENGTH_SHORT).show();
            return false;
        }
        // All inputs are valid
        return true;
    }
    }


