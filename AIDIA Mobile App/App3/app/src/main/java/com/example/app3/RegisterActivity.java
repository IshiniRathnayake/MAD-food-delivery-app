package com.example.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, addressEditText, zipcodeEditText, phoneEditText;
    private Spinner districtSpinner, daySpinner, monthSpinner, yearSpinner;
    private Button registerButton;
    private DatabaseHelper databaseHelper;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize ImageView and set OnClickListener
        backImageView = findViewById(R.id.backimage);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optionally close RegisterActivity
            }
        });

        // Initialize EditText fields and Button
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        addressEditText = findViewById(R.id.addressEditText);
        zipcodeEditText = findViewById(R.id.zipCodeEditText); // Corrected ID
        phoneEditText = findViewById(R.id.phoneEditText);

        registerButton = findViewById(R.id.registerButton);
        databaseHelper = new DatabaseHelper(this);

        // Initialize Spinners
        daySpinner = findViewById(R.id.daySpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        districtSpinner = findViewById(R.id.districtSpinner);

        // Populate day spinner
        List<String> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(String.valueOf(i));
        }
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // Populate month spinner
        List<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // Populate year spinner
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // Populate district spinner
        List<String> districts = new ArrayList<>();
        districts.add("Colombo");
        districts.add("Gampaha");
        districts.add("Kalutara");
        districts.add("Kandy");
        districts.add("Matale");
        districts.add("Nuwara Eliya");
        districts.add("Galle");
        districts.add("Matara");
        districts.add("Hambantota");
        districts.add("Jaffna");
        districts.add("Kilinochchi");
        districts.add("Mannar");
        districts.add("Vavuniya");
        districts.add("Mullaitivu");
        districts.add("Batticaloa");
        districts.add("Ampara");
        districts.add("Trincomalee");
        districts.add("Polonnaruwa");
        districts.add("Anuradhapura");
        districts.add("Kurunegala");
        districts.add("Puttalam");
        districts.add("Rathnapura");
        districts.add("Kegalle");
        districts.add("Badulla");
        districts.add("Monaragala");
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);

        // Handle registration button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String district = districtSpinner.getSelectedItem().toString();
                String zipcode = zipcodeEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                String day = daySpinner.getSelectedItem().toString();
                String month = monthSpinner.getSelectedItem().toString();
                String year = yearSpinner.getSelectedItem().toString();
                String birthday = day + "/" + month + "/" + year;

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() ||
                        district.isEmpty() || zipcode.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = databaseHelper.insertUser(username, email, password, birthday, address, district, zipcode, phone);
                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        // Save login status in SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        // Navigate to DashboardActivity after successful registration
                        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish(); // Close RegisterActivity
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
