package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditCardActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextInputEditText cardNameEditText, cardNumberEditText, expiryEditText, cvvEditText;
    private Button saveCardEditButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcard);

        backButton = findViewById(R.id.backButton);
        cardNameEditText = findViewById(R.id.Cardusernamesaved);
        cardNumberEditText = findViewById(R.id.Cardnumbersaved);
        expiryEditText = findViewById(R.id.expirysaved);
        cvvEditText = findViewById(R.id.cvvsaved);
        saveCardEditButton = findViewById(R.id.savecardedit);

        // Set up Firebase Firestore and FirebaseAuth
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Retrieve user's card information from Firestore and populate the EditText fields
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            db.collection("UserCards")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Retrieve card details and set them in the EditText fields
                                CardModel cardModel = document.toObject(CardModel.class);
                                if (cardModel != null) {
                                    cardNameEditText.setText(cardModel.getCardName());
                                    cardNumberEditText.setText(cardModel.getCardNumber());
                                    expiryEditText.setText(cardModel.getExpiry());
                                    cvvEditText.setText(cardModel.getCvv());
                                }
                            }
                        } else {
                            Toast.makeText(EditCardActivity.this, "Error retrieving card details", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the ProfileActivity
                Intent intent = new Intent(EditCardActivity.this, ProfileActivity.class);
                startActivity(intent);
                // Finish the current activity
                finish();
            }
        });


        saveCardEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save edited card details to Firestore
                saveEditedCardToFirestore();
            }
        });

    }

    private void saveEditedCardToFirestore() {
        // Retrieve edited card details from EditText fields
        String editedCardName = cardNameEditText.getText().toString();
        String editedCardNumber = cardNumberEditText.getText().toString();
        String editedExpiry = expiryEditText.getText().toString();
        String editedCvv = cvvEditText.getText().toString();

        // Update card details in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            CardModel editedCardModel = new CardModel(editedCardName, editedCardNumber, editedExpiry, editedCvv);

            db.collection("UserCards")
                    .document(userId)
                    .set(editedCardModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Card details saved successfully
                            Toast.makeText(EditCardActivity.this, "Card details updated successfully", Toast.LENGTH_SHORT).show();

                            // Navigate to the ProfileActivity
                            Intent intent = new Intent(EditCardActivity.this, ProfileActivity.class);
                            startActivity(intent);

                            // Finish the current activity
                            finish();
                        } else {
                            // Failed to save edited card details
                            Toast.makeText(EditCardActivity.this, "Failed to update card details", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}

