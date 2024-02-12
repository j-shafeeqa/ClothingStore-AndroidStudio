package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private MyCartAdapter cartAdapter;
    private List<MyCartModel> cartModelList;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private TextView textViewSubtotal;
    private TextView textViewDeliveryFee;
    private TextView textViewTotal;
    Button checkoutButton,backButton;

    // Constant delivery fee
    private static final double DELIVERY_FEE = 7.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);
        bottomNavigation.show(2, true);

        // Other code specific to CartActivity
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerViewCart);
        textViewSubtotal = findViewById(R.id.textViewSubtotal);
        textViewDeliveryFee = findViewById(R.id.textViewDeliveryFee);
        textViewTotal = findViewById(R.id.textViewTotal);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        checkoutButton = findViewById(R.id.checkoutButton);

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            double totalPrice = 0.0; // Initialize total price
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                                String documentId = documentSnapshot.getId();

                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setImageUrl(documentSnapshot.getString("imageUrl"));
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);

                                // Update total price for each item
                                totalPrice += Double.parseDouble(cartModel.getTotalPrice());
                            }

                            // Calculate subtotal and total
                            double subtotal = totalPrice;
                            double total = subtotal + DELIVERY_FEE;

                            // Set the calculated values to the TextViews
                            textViewSubtotal.setText(String.format("%.2f", subtotal));
                            textViewDeliveryFee.setText(String.format("%.2f", DELIVERY_FEE));
                            textViewTotal.setText(String.format("%.2f", total));

                            // Notify the adapter once after adding all items
                            cartAdapter.notifyDataSetChanged();
                        }
                    }
                });

        // Add Items button
        Button btnAddItems = findViewById(R.id.Additems);
        btnAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the main page (assuming MainActivity is your main page)
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to remove it from the stack
            }
        });

        // Set click listener for the Checkout button
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, for example, start a new activity
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }
}



