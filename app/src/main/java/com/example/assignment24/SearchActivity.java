package com.example.assignment24;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);
        bottomNavigation.show(2, true);

        // Other code specific to SearchActivity
    }
}
