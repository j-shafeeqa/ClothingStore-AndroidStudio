package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Prod1Activity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView productDescriptionTextView;

    private ImageView productImageView;

    private Button addtocartprodbutton;
    private ImageButton decrementButton;
    private ImageButton incrementButton;
    private TextView quantityTextView;
    private TextView totalprice;
    private int quantity = 1;

    FirebaseAuth auth;
    CategoryModel categoryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod1);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);

        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Find views by their IDs
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        addtocartprodbutton = findViewById(R.id.addtocartbutton);
        productImageView = findViewById(R.id.productImageView);
        ImageButton backButton = findViewById(R.id.backButton);
        decrementButton = findViewById(R.id.decrementButton);
        incrementButton = findViewById(R.id.incrementButton);
        quantityTextView = findViewById(R.id.quantityTextView);
        totalprice = findViewById(R.id.totalprice);

        // Retrieve product ID from the intent
        String productId = getIntent().getStringExtra("productId");

        // Set OnClickListener for the ImageButton
        backButton.setOnClickListener(v -> {
            // Handle the click event here
            //navigate to MainActivity
            Intent intent = new Intent(Prod1Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        //onclcik listeners for quantity
        decrementButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        incrementButton.setOnClickListener(v -> {
            quantity++;
            quantityTextView.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        // Set click listener for the "Add to Cart" button
        addtocartprodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedtoCart();

            }
        });

        // Query Firestore to get product details
        if (productId != null) {
            getProductDetails(productId);
        }
    }

    //add to cart function
    private void addedtoCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("name", categoryModel.getName());
        cartMap.put("price", productPriceTextView.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantityTextView.getText().toString());
        cartMap.put("totalPrice", totalprice.getText().toString());
        // Add the image URL to the cartMap
        cartMap.put("imageUrl", categoryModel.getImg_url1());

        db.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("CurrentUser")
                .add(cartMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Prod1Activity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Prod1Activity.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getProductDetails(String productId) {
        // Query Firestore to get product details based on the product ID
        db.collection("Category").document(productId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Map the document snapshot to your CategoryModel or use the data directly
                        categoryModel = documentSnapshot.toObject(CategoryModel.class);

                        // Display product details in the UI
                        if (categoryModel != null) {
                            productNameTextView.setText(categoryModel.getName());
                            productPriceTextView.setText(categoryModel.getPrice());
                            productDescriptionTextView.setText(categoryModel.getDescription());

                            // Set totalprice with trimmed price (without "AED" prefix)
                            String trimmedPrice = trimAED(categoryModel.getPrice());
                            totalprice.setText(trimmedPrice);

                            // Load the product image using Picasso
                            Picasso.get()
                                    .load(categoryModel.getImg_url1())
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_launcher_background)
                                    .into(productImageView);
                        }
                    } else {
                        // Handle the case where the document doesn't exist
                        Toast.makeText(Prod1Activity.this, "Product not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failures, e.g., network errors or Firestore errors
                    Log.e("Prod1Activity", "Error getting product details", e);
                });
    }

    private String trimAED(String priceWithAED) {
        // Trim "AED" from the price and remove leading/trailing spaces
        return priceWithAED.replace("AED", "").trim();
    }


    // Method to calculate and update total price
    private void updateTotalPrice() {


        // Assuming productPriceTextView contains a price string like "AED 10.00"
        String productPriceString = productPriceTextView.getText().toString();

        // Extract the numeric part of the price
        String priceNumericString = productPriceString.substring(productPriceString.indexOf(" ") + 1);

        // Convert the price to a double value
        double price = Double.parseDouble(priceNumericString);

        // Calculate the total price based on quantity
        double totalPrice = price * quantity;

        // Format the total price to display in 0.00 format
        String formattedTotalPrice = String.format("%.2f", totalPrice);

        // Update the total price TextView
        TextView totalPriceTextView = findViewById(R.id.totalprice);
        totalPriceTextView.setText(formattedTotalPrice);
    }



}
