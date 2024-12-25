package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private String userEmail; // Store the user's email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        userEmail = preferences.getString("user_email", null); // Retrieve the user's email

        if (!isLoggedIn || userEmail == null) {
            // Redirect to LoginActivity if not logged in
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish(); // Close DashboardActivity
            return;
        }

        setContentView(R.layout.activity_dashboard);

        // Get buttons from the layout
        Button profileButton = findViewById(R.id.profileButton);
        Button foodSelectionButton = findViewById(R.id.foodSelectionButton);
        Button cartButton = findViewById(R.id.cartButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button contactUsButton = findViewById(R.id.contactUsButton);
        // Set click listeners
        profileButton.setOnClickListener(v -> {
            // Navigate to User Profile and pass the user's email
            Intent intent = new Intent(DashboardActivity.this, UserProfileActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
        });

        foodSelectionButton.setOnClickListener(v -> {
            // Navigate to Food Selection
            startActivity(new Intent(DashboardActivity.this, FoodSelectionActivity.class));
        });

        cartButton.setOnClickListener(v -> {
            // Navigate to Cart
            startActivity(new Intent(DashboardActivity.this, CartActivity.class));
        });

        contactUsButton.setOnClickListener(v -> {
            // Navigate to Contact Us
            Intent intent = new Intent(DashboardActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Handle logout and navigate to the login page
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("user_email"); // Remove user's email on logout
            editor.apply(); // Clear login status
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish(); // Close DashboardActivity
        });

    }
}
