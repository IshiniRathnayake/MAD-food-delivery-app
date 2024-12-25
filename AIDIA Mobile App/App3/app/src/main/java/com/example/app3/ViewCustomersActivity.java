package com.example.app3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewCustomersActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView customersRecyclerView;
    private CustomersAdapter customersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customers);

        databaseHelper = new DatabaseHelper(this);
        customersRecyclerView = findViewById(R.id.customersRecyclerView);

        // Load customer data
        List<Customer> customers = loadCustomerData();
        // No need to check for null since it's initialized as an empty list if loading fails
        customersAdapter = new CustomersAdapter(customers);
        customersRecyclerView.setAdapter(customersAdapter);
        customersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Customer> loadCustomerData() {
        List<Customer> customers = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor != null) {
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
            int phoneIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE);

            // Check if column indexes are valid
            if (usernameIndex == -1 || emailIndex == -1 || phoneIndex == -1) {
                Toast.makeText(this, "Database columns not found", Toast.LENGTH_SHORT).show();
                cursor.close();
                return customers;
            }

            while (cursor.moveToNext()) {
                String username = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);
                // Add other fields as needed

                Customer customer = new Customer(username, email, phone);
                customers.add(customer);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Failed to load customers", Toast.LENGTH_SHORT).show();
        }
        return customers;
    }
}
