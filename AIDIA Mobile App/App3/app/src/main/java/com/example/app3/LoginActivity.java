package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ImageView backImageView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views and database helper
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        backImageView = findViewById(R.id.backimage);
        databaseHelper = new DatabaseHelper(this);

        // Check if user is already logged in
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
        String userEmail = preferences.getString("user_email", null);

        if (isLoggedIn && userEmail != null) {
            // Navigate to DashboardActivity if already logged in
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);
            finish(); // Close LoginActivity
            return; // Exit onCreate to avoid additional setup
        }

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Perform login validation
            if (databaseHelper.checkUser(email, password)) {
                // Save login status in SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("user_email", email); // Save the email
                editor.apply();

                // Navigate to DashboardActivity after successful login
                Intent dashboardIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                dashboardIntent.putExtra("user_email", email); // Pass the logged-in user's email
                startActivity(dashboardIntent);
                finish(); // Close LoginActivity
            } else {
                // Show error message
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        backImageView.setOnClickListener(v -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity
        });
    }
}
