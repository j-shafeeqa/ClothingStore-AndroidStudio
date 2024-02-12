package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private VideoView videoView;
    private TextView usernameTextView, emailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);
        bottomNavigation.show(3, true);

        // Other code specific to ProfileActivity

        TextView editProfileTextView = findViewById(R.id.editProfileTextView);
        ImageButton editProfileButton = findViewById(R.id.editprofilebutton);

        TextView shoppingAddressTextView = findViewById(R.id.shoppingAddressTextView);
        ImageButton shoppingAddressButton = findViewById(R.id.shoppingaddressbutton);

        TextView cardsTextView = findViewById(R.id.cardsTextView);
        ImageButton cardsButton = findViewById(R.id.cardsbutton);

        TextView logoutTextView = findViewById(R.id.logoutTextView);
        ImageButton logoutButton = findViewById(R.id.logoutbutton);
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        showAllUserData();


            editProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open EditProfileActivity
                    startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                }
            });

            shoppingAddressTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open AddressActivity
                    startActivity(new Intent(ProfileActivity.this, EditLocationActivity.class));
                }
            });

            shoppingAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open AddressActivity
                    startActivity(new Intent(ProfileActivity.this, EditLocationActivity.class));
                }
            });

            cardsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open CardsActivity
                    startActivity(new Intent(ProfileActivity.this, EditCardActivity.class));
                }
            });

            cardsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open CardsActivity
                    startActivity(new Intent(ProfileActivity.this, EditCardActivity.class));
                }
            });

            // Set click listeners
            logoutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Implement logout logic using Firebase Authentication
                    FirebaseAuth.getInstance().signOut();

                    // Redirect to LoginActivity after logout
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity to prevent going back
                }
            });

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Implement logout logic using Firebase Authentication
                    FirebaseAuth.getInstance().signOut();

                    // Redirect to LoginActivity after logout
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity to prevent going back
                }
            });
        }
    public void showAllUserData() {
        // Get the current Firebase user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Retrieve user information from Firebase Realtime Database
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(currentUser.getUid());

            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        HelperClass user = dataSnapshot.getValue(HelperClass.class);

                        if (user != null) {
                            // Set the retrieved data to the TextViews
                            emailTextView.setText(user.getEmail());
                            usernameTextView.setText(user.getUsername());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }
    }

}


