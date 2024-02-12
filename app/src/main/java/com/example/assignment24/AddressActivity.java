package com.example.assignment24;



import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.chip.Chip;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class AddressActivity extends AppCompatActivity {

    private TextInputEditText buildingNameEditText;
    private TextInputEditText roomNumberEditText;
    private TextInputEditText floorNumberEditText;
    private TextInputEditText streetNameEditText;
    private TextInputEditText additionalEditText;
    private TextInputEditText phoneNumberEditText;
    private ChipGroup chipgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);

        // Find views by their IDs
        buildingNameEditText = findViewById(R.id.BuildingName);
        roomNumberEditText = findViewById(R.id.roomnumber);
        floorNumberEditText = findViewById(R.id.floorno);
        streetNameEditText = findViewById(R.id.Streetname);
        additionalEditText = findViewById(R.id.Additional);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        // Initialize ChipGroup
        chipgrp = findViewById(R.id.chipgrp);

        // Set input filters for room number and floor number
        roomNumberEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        floorNumberEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        // Set click listener for the "Save Address" button
        Button saveAddressButton = findViewById(R.id.buttonOrderNow);
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });
    }

    private void saveAddress() {
        // Retrieve the address details from EditText fields
        String buildingName = buildingNameEditText.getText().toString();
        String roomNumber = roomNumberEditText.getText().toString();
        String floorNumber = floorNumberEditText.getText().toString();
        String streetName = streetNameEditText.getText().toString();
        String additionalDirections = additionalEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        // Retrieve the selected chip text from the ChipGroup
        String selectedChipText = getSelectedChipText();

        // Validate and save the address information to Firestore
        if (validateInputs(buildingName, roomNumber, floorNumber, streetName, additionalDirections, phoneNumber, selectedChipText)) {
            saveAddressToFirestore(buildingName, roomNumber, floorNumber, streetName, additionalDirections, phoneNumber, selectedChipText);
        }
    }

    private String getSelectedChipText() {
        // Get the checked chip id from the ChipGroup
        int checkedChipId = chipgrp.getCheckedChipId();

        // Find the checked chip by id
        Chip selectedChip = chipgrp.findViewById(checkedChipId);

        // If a chip is selected, return its text, otherwise return an empty string
        return (selectedChip != null) ? selectedChip.getText().toString() : "";
    }

    private boolean validateInputs(String buildingName, String roomNumber, String floorNumber, String streetName, String additionalDirections, String phoneNumber, String selectedChipText) {
        // Check if any field is empty
        if (buildingName.isEmpty() || roomNumber.isEmpty() || floorNumber.isEmpty() || streetName.isEmpty() || phoneNumber.isEmpty() || selectedChipText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate building name (allow alphanumeric and spaces)
        if (!buildingName.matches("[a-zA-Z0-9 ]+")) {
            Toast.makeText(this, "Building name should only contain numbers, alphabets, and spaces", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate room number and floor number (allow only numbers)
        if (!roomNumber.matches("[0-9]+") || !floorNumber.matches("[0-9]+")) {
            Toast.makeText(this, "Room number and floor number should only contain numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    // Helper method to check if a string is alphanumeric
    private boolean isAlphaNumeric(String s) {
        return s.matches("[a-zA-Z0-9]+");
    }


    private void saveAddressToFirestore(String buildingName, String roomNumber, String floorNumber, String streetName, String additionalDirections, String phoneNumber, String selectedChipText) {
        // Use a HashMap to store address details
        HashMap<String, Object> addressMap = new HashMap<>();
        addressMap.put("buildingName", buildingName);
        addressMap.put("roomNumber", roomNumber);
        addressMap.put("floorNumber", floorNumber);
        addressMap.put("streetName", streetName);
        addressMap.put("additionalDirections", additionalDirections);
        addressMap.put("phoneNumber", phoneNumber);
        addressMap.put("addressType", selectedChipText);

        // Save the address details to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            db.collection("UserAddresses")
                    .document(userId)
                    .set(addressMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Address saved successfully
                            Toast.makeText(AddressActivity.this, "Address saved successfully", Toast.LENGTH_SHORT).show();

                            // Navigate to CardActivity
                            Intent intent = new Intent(AddressActivity.this, CardActivity.class);
                            startActivity(intent);

                            // Finish the current activity
                            finish();
                        } else {
                            // Failed to save address
                            Toast.makeText(AddressActivity.this, "Failed to save address", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}

