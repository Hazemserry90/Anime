package com.example.testing;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class StartFragment extends Fragment {


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize the NavController for navigation
        NavController navController = Navigation.findNavController(view);
        // Find the buttons by their IDs
        Button button = view.findViewById(R.id.startgame);
        Button button2 = view.findViewById(R.id.register);
        // Set an OnClickListener for the "startgame" button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate from startFragment to gameFragment
                navController.navigate(R.id.action_startFragment_to_gameFragment);

            }
        });
        // Set an OnClickListener for the "register" button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate from startFragment to endgameFragment
                navController.navigate(R.id.action_startFragment_to_endgameFragment);
            }
        });
    }
}

