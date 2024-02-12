package com.example.assignment24;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLocationActivity extends AppCompatActivity {

    private EditText buildingNameEditText, roomNumberEditText, floorNumberEditText,
            streetNameEditText, additionalEditText, phoneNumberEditText;
    private Button saveButton;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlocation);

        // Initialize EditText and Button
        buildingNameEditText = findViewById(R.id.BuildingName);
        roomNumberEditText = findViewById(R.id.roomnumber);
        floorNumberEditText = findViewById(R.id.floornoo);
        streetNameEditText = findViewById(R.id.Streetname);
        additionalEditText = findViewById(R.id.Additional);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        saveButton = findViewById(R.id.save);

        // Retrieve user's UID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            // Retrieve data from Firestore
            FirebaseFirestore.getInstance()
                    .collection("UserAddresses")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Convert the document data to AddressModel
                                AddressModel addressModel = document.toObject(AddressModel.class);
                                if (addressModel != null) {
                                    // Update the UI with the obtained address data
                                    updateUIWithAddressData(addressModel);
                                }
                            }
                        }
                    });
        }

        // Set click listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the edited address data
                saveEditedAddress();
            }
        });
    }

    private void updateUIWithAddressData(AddressModel addressModel) {
        // Update EditTexts with address data
        buildingNameEditText.setText(addressModel.getBuildingName());
        roomNumberEditText.setText(addressModel.getRoomNumber());
        floorNumberEditText.setText(addressModel.getFloorNumber());
        streetNameEditText.setText(addressModel.getStreetName());
        additionalEditText.setText(addressModel.getAdditionalDirections());
        phoneNumberEditText.setText(addressModel.getPhoneNumber());
    }

    private void saveEditedAddress() {
        // Get the edited data from EditTexts
        String buildingName = buildingNameEditText.getText().toString();
        String roomNumber = roomNumberEditText.getText().toString();
        String floorNumber = floorNumberEditText.getText().toString();
        String streetName = streetNameEditText.getText().toString();
        String additionalDirections = additionalEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();

        AddressModel editedAddress = new AddressModel(
                userId, buildingName, roomNumber, floorNumber, streetName,
                additionalDirections, phoneNumber, "Apartment");

        // Update the edited data in Firestore
        DocumentReference addressRef = FirebaseFirestore.getInstance()
                .collection("UserAddresses")
                .document(userId);

        addressRef.set(editedAddress)
                .addOnSuccessListener(aVoid -> {
                    // Address updated successfully
                    // Update the UI to reflect the changes
                    updateUIWithAddressData(editedAddress);
                    Toast.makeText(EditLocationActivity.this, "Address saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    // You can add an error message or log the error
                    Toast.makeText(EditLocationActivity.this, "Failed to save address", Toast.LENGTH_SHORT).show();
                });
    }
}

