package com.example.assignment24;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ViewPager viewPager;
    WormDotsIndicator dot3;
    ViewAdapter viewAdapter;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> categoryArrayList;
    CategoryAdapter myAdapter;
    FirebaseFirestore db;

    private List<CategoryModel> categoryModelList;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);
        BottomNavigationConfig.configure(bottomNavigation, this);
        bottomNavigation.show(1, true);

        // Code for dotindicator and viewadapter for ads
        viewPager = findViewById(R.id.view_pager);
        dot3 = findViewById(R.id.dot3);
        viewAdapter = new ViewAdapter(this);
        viewPager.setAdapter(viewAdapter);
        dot3.setViewPager(viewPager);


        recyclerView = findViewById(R.id.rec_category);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        db = FirebaseFirestore.getInstance();
        categoryArrayList = new ArrayList<>();
        myAdapter = new CategoryAdapter(MainActivity.this, categoryArrayList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryModel category) {
                // Handle item click
                openProductDetailsActivity(category);
            }
        });

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();



    }

    private void EventChangeListener() {
        db.collection("Category")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, "Error getting documents.", e);
                            return;
                        }

                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    CategoryModel category = new CategoryModel(dc.getDocument().getId(), dc.getDocument().getString("img_url1"), dc.getDocument().getString("name"), dc.getDocument().getString("price"), dc.getDocument().getString("description"), dc.getDocument().getString("type"));
                                    categoryArrayList.add(category);
                                    break;
                                case MODIFIED:
                                    // Handle modified document
                                    break;
                                case REMOVED:
                                    // Handle removed document
                                    break;
                            }
                        }

                        myAdapter.notifyDataSetChanged();
                    }
                });
    }




    private void openProductDetailsActivity(CategoryModel category) {
        // Check the product name and open the corresponding activity
        String productName = category.getName();

        Intent intent;

        // Example: Open Prod1Activity for the product "Floral Flared Heels"
        if ("Floral Flared Heels".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod1Activity.class);
        } else if ("Textured Plisse Wide Leg Pants".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod2Activity.class);
        } else if ("Textured Twofer Jumper".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod3Activity.class);
        } else if ("Ribbed Dress".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod4Activity.class);
        } else if ("Plain Hooded Sweatshirt".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod5Activity.class);
        } else if ("Ribbon Bow Clip".equals(productName)) {
            intent = new Intent(MainActivity.this, Prod6Activity.class);
        } else {
            Toast.makeText(MainActivity.this, "Product details not available", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra("productId", category.getDocumentId());

        startActivity(intent);
    }


    private boolean isUserLoggedIn() {
        // Check if the user is logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser != null;
    }


}
