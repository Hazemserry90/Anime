package com.example.testing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavbarFragment extends Fragment {


    public NavbarFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_navbar, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Find the BottomNavigationView by its ID
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.nav_comp_bar);

        // Load the default fragment (e.g., HomeFragment)
        loadFragment(new HomeFragment());

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
// Determine which item was selected in the BottomNavigationView
            
            if (item.getItemId() == R.id.homep) {
                // If "Home" is selected, instantiate HomeFragment
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.profile) {
                // If "Profile" is selected, instantiate ProfileFragment
                selectedFragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.settings) {
                // If "Settings" is selected, instantiate SettingsFragment
                selectedFragment = new SettingsFragment();
            }

            // Load the selected fragment
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;  // Return true to indicate that the navigation event was handled
        });
    }

    private void loadFragment(Fragment fragment) {
// Begin a fragment transaction to replace the current fragment
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment) // Replace the FrameLayout's content with the new fragment
                .commit(); // Commit the transaction
    }

    }

