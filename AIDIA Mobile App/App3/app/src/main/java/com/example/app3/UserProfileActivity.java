package com.example.app3;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private EditText usernameEditText, emailEditText, phoneEditText, birthdayEditText, addressEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize the views
        profileImageView = findViewById(R.id.profileImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        birthdayEditText = findViewById(R.id.birthdayEditText);
        addressEditText = findViewById(R.id.addressEditText);
        saveButton = findViewById(R.id.saveButton);

        // Get the user's email from the intent
        String userEmail = getIntent().getStringExtra("user_email");

        if (userEmail == null) {
            // Handle the case where email is not provided
            Toast.makeText(this, "Error: No email provided", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
            return;
        }

        // Fetch the user data from the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getUser(userEmail);

        if (cursor != null && cursor.moveToFirst()) {
            usernameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            emailEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            phoneEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            birthdayEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("birthday")));
            addressEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("district")));
            cursor.close();


    } else {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if user not found
            if (cursor != null) {
                cursor.close();
            }
        }

        // Handle the Save button click
        saveButton.setOnClickListener(v -> {
            // Save the updated user data
            ContentValues values = new ContentValues();
            values.put("username", usernameEditText.getText().toString());
            values.put("phone", phoneEditText.getText().toString());
            values.put("birthday", birthdayEditText.getText().toString());
            values.put("address", addressEditText.getText().toString());

            // Update the user data in the database
            boolean success = dbHelper.updateUser(userEmail, values);

            if (success) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after saving
            } else {
                Toast.makeText(this, "Error updating profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
