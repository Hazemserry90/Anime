package com.example.testing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;               // Firebase Authentication instance
    private DatabaseReference database;      // Firebase Realtime Database reference

    private TextView emailTextView;          // TextView to display email
    private EditText usernameEditText;       // EditText to update username
    private Button saveButton;               // Button to save updated profile details
    private Button logoutButton;             // Button to log out the user


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        // Bind UI elements
        emailTextView = view.findViewById(R.id.emailTextView);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        saveButton = view.findViewById(R.id.saveButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Load user profile data
        loadUserProfile();

        // Set listener for Save button
        saveButton.setOnClickListener(v -> {
            String newUsername = usernameEditText.getText().toString().trim();
            if (TextUtils.isEmpty(newUsername)) {
                Toast.makeText(getContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            updateUsername(newUsername);
        });

        // Set listener for Logout button
        logoutButton.setOnClickListener(v -> {
            auth.signOut();
            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            // Navigate back to login fragment or activity
            // Replace `R.id.action_profileFragment_to_loginFragment` with the actual navigation ID
        });
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Display email from Firebase Authentication
            emailTextView.setText(currentUser.getEmail());

            // Fetch username from Firebase Realtime Database
            database.child(currentUser.getUid());
            database.child("username");
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String username = snapshot.getValue(String.class);
                        usernameEditText.setText(username);
                    } else {
                        usernameEditText.setHint("Enter your username");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        } else {
            Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUsername(String username) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            database.child(currentUser.getUid()).child("username").setValue(username)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Username updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to update username: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}