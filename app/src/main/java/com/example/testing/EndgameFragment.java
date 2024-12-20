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
import com.example.testing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EndgameFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endgame, container, false);

    }
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            // Call the superclass method to ensure proper initialization
            super.onViewCreated(view, savedInstanceState);
            // Initialize Firebase Authentication and Realtime Database
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance().getReference("users");

            EditText usernameField = view.findViewById(R.id.username); // Input field for username
            EditText emailField = view.findViewById(R.id.email); // Input field for email
            EditText passwordField = view.findViewById(R.id.password); // Input field for password

            final NavController navController = Navigation.findNavController(view);

            Button button = view.findViewById(R.id.signup);
            button.setOnClickListener(v -> {
                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (validateInputs(username, email, password)) {
                    signUpUser(username, email, password, navController);
                }
            });
        }

    private boolean validateInputs(String name, String email, String password) {
        String usernameRegex = "^[A-Za-z\\s]{2,}$";
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";

        if (!name.matches(usernameRegex)) {
            Toast.makeText(getContext(), "Name must contain only letters and spaces, with at least 2 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.matches(emailRegex)) {
            Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches(passwordRegex)) {
            Toast.makeText(getContext(), "Password must be at least 6 characters long and include at least one letter and one number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void signUpUser(String username, String email, String password, NavController navController) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the signed-in user
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserToDatabase(user.getUid(), username, email);
                        }

                        Toast.makeText(getContext(), "Sign-Up successful", Toast.LENGTH_SHORT).show();

                        // Navigate to StartFragment
                        navController.navigate(R.id.action_endgameFragment_to_startFragment);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getContext(), "Email already in use", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sign-Up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToDatabase(String userId, String username, String email) {
        User user = new User(username, email); // Custom class to represent user data
        database.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "User data saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Custom User class to store user details in Firebase Realtime Database
    public static class User {
        public String username;
        public String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}



