package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.Button;


public class CheckoutActivity extends AppCompatActivity {

    private TextView addressTypeTextView;
    private TextView streetNameTextView;
    private TextView buildingNameTextView;
    private TextView floorNumberTextView;
    private TextView roomNumberTextView;


    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUid = auth.getCurrentUser().getUid();

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);

        // Retrieve and display user address data
        retrieveUserAddressData();

        // Retrieve and display user card data
        retrieveUserCardData();

        // Set click listener for the "Place Order" button
        Button placeOrderButton = findViewById(R.id.btnPlaceOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform card verification and place order
                verifyCardAndPlaceOrder();
            }
        });
    }

    private void retrieveUserAddressData() {
        db.collection("UserAddresses")
                .document(currentUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve address data and update UI
                            AddressModel addressModel = document.toObject(AddressModel.class);
                            if (addressModel != null) {
                                updateUIWithAddressData(addressModel);
                            }
                        } else {
                            Toast.makeText(CheckoutActivity.this, "Address information not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("CheckoutActivity", "Error getting user address information", task.getException());
                    }
                });
    }

    private void updateUIWithAddressData(AddressModel addressModel) {
        // Update TextViews with address data
        addressTypeTextView = findViewById(R.id.addresstype);
        streetNameTextView = findViewById(R.id.Streetname);
        buildingNameTextView = findViewById(R.id.bldgname);
        floorNumberTextView = findViewById(R.id.floor);
        roomNumberTextView = findViewById(R.id.room);

        addressTypeTextView.setText(addressModel.getAddressType());
        streetNameTextView.setText(addressModel.getStreetName());
        buildingNameTextView.setText(addressModel.getBuildingName());
        floorNumberTextView.setText("Floor Number: " + addressModel.getFloorNumber());
        roomNumberTextView.setText("Room Number: " + addressModel.getRoomNumber());
    }


    private void retrieveUserCardData() {
        db.collection("UserCards")
                .document(currentUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve card data and update UI
                            CardModel cardModel = document.toObject(CardModel.class);
                            if (cardModel != null) {
                                updateUIWithCardData(cardModel);
                            }
                        } else {
                            Toast.makeText(CheckoutActivity.this, "Card information not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("CheckoutActivity", "Error getting user card information", task.getException());
                    }
                });
    }

    private void updateUIWithCardData(CardModel cardModel) {
        // Update TextView with card data
        TextView cardNumberTextView = findViewById(R.id.Cardnumber);
        cardNumberTextView.setText(cardModel.getCardNumber());
    }

    private void verifyCardAndPlaceOrder() {
        // Retrieve entered CVV and expiry from EditTexts
        TextView cvvTextView = findViewById(R.id.cvv);
        TextView expiryTextView = findViewById(R.id.expiry);

        String enteredCVV = cvvTextView.getText().toString();
        String enteredExpiry = expiryTextView.getText().toString();

        // Validate expiry format (MM/YY)
        if (!isValidExpiry(enteredExpiry)) {
            expiryTextView.setError("Invalid expiry format (MM/YY)");
            return;
        }

        // Validate CVV format (3 digits)
        if (!isValidCVV(enteredCVV)) {
            cvvTextView.setError("Invalid CVV");
            return;
        }

        // Retrieve stored card information
        db.collection("UserCards")
                .document(currentUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Retrieve card data and verify CVV and expiry
                            CardModel cardModel = document.toObject(CardModel.class);
                            if (cardModel != null && cardModel.getCvv().equals(enteredCVV) && cardModel.getExpiry().equals(enteredExpiry)) {
                                // Verification successful, proceed to OrderDoneActivity
                                Intent intent = new Intent(CheckoutActivity.this, OrderdoneActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Verification failed
                                Toast.makeText(CheckoutActivity.this, "Card verification failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CheckoutActivity.this, "Card information not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("CheckoutActivity", "Error getting user card information", task.getException());
                    }
                });
    }

    private boolean isValidExpiry(String expiry) {
        // Validate expiry format (MM/YY)
        return expiry.matches("\\d{2}/\\d{2}");
    }


    private boolean isValidCVV(String cvv) {
        // Validate CVV format (3 digits)
        return cvv.length() == 3;
    }

}




