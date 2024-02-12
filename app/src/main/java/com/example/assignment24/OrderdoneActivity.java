package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class OrderdoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdone);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);

        // Find the "Continue Shopping" button by its ID
        Button continueShoppingButton = findViewById(R.id.goback);

        // Set a click listener for the button
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the button is clicked, navigate back to the main activity
                Intent intent = new Intent(OrderdoneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

