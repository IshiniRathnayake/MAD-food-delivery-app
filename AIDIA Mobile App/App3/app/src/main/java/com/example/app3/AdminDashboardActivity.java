package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Find the ImageButton for viewing customers
        ImageButton cusImageButton = findViewById(R.id.cusImageButton);
        // Find the ImageButton for logout
        ImageButton logoutButton = findViewById(R.id.logoutImageButton);
        // Find the ImageButton for viewing orders
        ImageButton orderImageButton = findViewById(R.id.orderImageButton);
        // Find the ImageButton for adding menu
        ImageButton addMenuButton = findViewById(R.id.addmenuButton);

        // Set onClick listener for the add menu button
        addMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminMenuActivity.class);
                startActivity(intent);
            }
        });

        // Set onClick listener for the view customers button
        cusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ViewCustomersActivity.class);
                startActivity(intent);
            }
        });

     /*   // Set onClick listener for the view orders button
        orderImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, ViewOrdersActivity.class);
                startActivity(intent);
            }
        });*/

        // Set onClick listener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the user session
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Redirect to the login page
                Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // End this activity
            }
        });
    }
}
