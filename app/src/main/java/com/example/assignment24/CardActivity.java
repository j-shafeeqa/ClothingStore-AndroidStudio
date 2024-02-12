package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CardActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextInputEditText cardNumberEditText, expiryEditText, cvvEditText;
    private Button saveCardButton;

    private TextInputEditText cardNameEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        backButton = findViewById(R.id.backButton);
        cardNumberEditText = findViewById(R.id.Cardnumber);
        expiryEditText = findViewById(R.id.expiry);
        cvvEditText = findViewById(R.id.cvv);
        saveCardButton = findViewById(R.id.savecard);
        cardNameEditText = findViewById(R.id.Cardusername);

        cvvEditText = findViewById(R.id.cvv);
        cvvEditText.addTextChangedListener(new CVVTextWatcher());


        // Add TextWatcher to format card number
        cardNumberEditText.addTextChangedListener(new CardNumberTextWatcher());

        // Add TextWatcher to format expiry
        expiryEditText.addTextChangedListener(new ExpiryTextWatcher());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to AddressActivity
                Intent intent = new Intent(CardActivity.this, AddressActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save card details to Firestore
                saveCardToFirestore();
            }
        });
    }

    private void saveCardToFirestore() {
        String cardNumber = cardNumberEditText.getText().toString();
        String expiry = expiryEditText.getText().toString();
        String cvv = cvvEditText.getText().toString();
        String cardName = cardNameEditText.getText().toString();

        // Validate and save the card information to Firestore
        if (validateInputs(cardNumber, expiry, cvv, cardName)) {
            // Use a HashMap to store card details
            HashMap<String, Object> cardMap = new HashMap<>();
            cardMap.put("cardNumber", cardNumber);
            cardMap.put("expiry", expiry);
            cardMap.put("cvv", cvv);
            cardMap.put("cardName",cardName);

            // Save the card details to Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                String userId = auth.getCurrentUser().getUid();

                db.collection("UserCards")
                        .document(userId)
                        .set(cardMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Card details saved successfully
                                Toast.makeText(CardActivity.this, "Card details saved successfully", Toast.LENGTH_SHORT).show();

                                // Navigate to CheckoutActivity
                                Intent intent = new Intent(CardActivity.this, CheckoutActivity.class);
                                startActivity(intent);

                                // Finish the current activity
                                finish();
                            } else {
                                // Failed to save card details
                                Toast.makeText(CardActivity.this, "Failed to save card details", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private boolean validateInputs(String cardNumber, String expiry, String cvv, String cardName) {
        // Implement your validation logic here

        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    class CardNumberTextWatcher implements TextWatcher {
        private static final int CARD_NUMBER_TOTAL_DIGITS = 16;
        private static final int CARD_NUMBER_CHUNK_SIZE = 4;
        private static final char CARD_NUMBER_SEPARATOR = ' ';

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String originalCardNumber = editable.toString();

            // Remove non-numeric characters from the original card number
            String strippedCardNumber = originalCardNumber.replaceAll("[^0-9]", "");

            // Break the card number into chunks of CARD_NUMBER_CHUNK_SIZE digits
            StringBuilder formattedCardNumber = new StringBuilder();
            int len = strippedCardNumber.length();
            for (int i = 0; i < len; i += CARD_NUMBER_CHUNK_SIZE) {
                int end = Math.min(i + CARD_NUMBER_CHUNK_SIZE, len);
                formattedCardNumber.append(strippedCardNumber, i, end);

                if (end < len) {
                    formattedCardNumber.append(CARD_NUMBER_SEPARATOR);
                }
            }

            // Set the formatted card number back to the EditText
            cardNumberEditText.removeTextChangedListener(this);
            cardNumberEditText.setText(formattedCardNumber);
            cardNumberEditText.setSelection(formattedCardNumber.length());
            cardNumberEditText.addTextChangedListener(this);
        }
    }

    class ExpiryTextWatcher implements TextWatcher {
        private static final int EXPIRY_TOTAL_DIGITS = 5; // Two digits before slash + one digit after + one slash
        private static final int SLASH_POSITION = 3;
        private static final char EXPIRY_SEPARATOR = '/';

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String originalExpiry = editable.toString();

            // Remove non-numeric characters from the original expiry
            String strippedExpiry = originalExpiry.replaceAll("[^0-9]", "");

            // Check if strippedExpiry is not empty and is not in the valid format
            if (!strippedExpiry.isEmpty() && strippedExpiry.length() > SLASH_POSITION) {
                // Insert the slash if it's missing
                if (strippedExpiry.charAt(SLASH_POSITION - 1) != EXPIRY_SEPARATOR) {
                    strippedExpiry = new StringBuilder(strippedExpiry).insert(SLASH_POSITION - 1, EXPIRY_SEPARATOR).toString();
                }

                // Limit to EXPIRY_TOTAL_DIGITS characters
                if (strippedExpiry.length() > EXPIRY_TOTAL_DIGITS) {
                    strippedExpiry = strippedExpiry.substring(0, EXPIRY_TOTAL_DIGITS);
                }
            }

            // Set the formatted expiry back to the EditText
            expiryEditText.removeTextChangedListener(this);
            expiryEditText.setText(strippedExpiry);
            expiryEditText.setSelection(strippedExpiry.length());
            expiryEditText.addTextChangedListener(this);
        }
    }

    class CardNameTextWatcher implements TextWatcher {
        private static final int CARD_NAME_TOTAL_CHARS = 50;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            // Ensure that only letters are accepted
            String input = charSequence.toString();
            if (!input.matches("[a-zA-Z ]*")) {
                // Show an error or handle the invalid input as needed
                cardNameEditText.setError("Only letters are allowed");
            } else {
                cardNameEditText.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Limit the card name length
            if (editable.length() > CARD_NAME_TOTAL_CHARS) {
                // Show an error or handle the exceeding length as needed
            }
        }
    }

    class CVVTextWatcher implements TextWatcher {
        private static final int CVV_TOTAL_DIGITS = 3; // Adjust as needed

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String originalCVV = editable.toString();

            // Remove non-numeric characters from the original CVV
            String strippedCVV = originalCVV.replaceAll("[^0-9]", "");

            // Limit the CVV length
            if (strippedCVV.length() > CVV_TOTAL_DIGITS) {
                strippedCVV = strippedCVV.substring(0, CVV_TOTAL_DIGITS);
            }

            // Set the formatted CVV back to the EditText
            cvvEditText.removeTextChangedListener(this);
            cvvEditText.setText(strippedCVV);
            cvvEditText.setSelection(strippedCVV.length());
            cvvEditText.addTextChangedListener(this);
        }
    }

}

