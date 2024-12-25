package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Check admin credentials
            if (email.equals("admin123@gmail.com") && password.equals("admin")) {
                // Navigate to AdminDashboardActivity
                Intent intent = new Intent(AdminLogin.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish(); // Close AdminLogin Activity
            } else {
                // Show error message
                Toast.makeText(AdminLogin.this, "Invalid Admin Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
