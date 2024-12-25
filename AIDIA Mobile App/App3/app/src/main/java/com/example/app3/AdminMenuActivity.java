package com.example.app3;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminMenuActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextItemName, editTextCategory, editTextAvailableItems, editTextPrice;
    private ListView listViewMenuItems;
    private ArrayAdapter<String> adapter;
    private List<String> menuItemsList = new ArrayList<>();
    private int selectedItemId = -1; // To track the selected item for updating

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        dbHelper = new DatabaseHelper(this);

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextAvailableItems = findViewById(R.id.editTextAvailableItems);
        editTextPrice = findViewById(R.id.editTextPrice);
        listViewMenuItems = findViewById(R.id.listViewMenuItems);

        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        Button buttonUpdateItem = findViewById(R.id.buttonUpdateItem);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenuItem();
            }
        });

        buttonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMenuItem();
            }
        });

        loadMenuItems();
    }

    private void addMenuItem() {
        String name = editTextItemName.getText().toString();
        String category = editTextCategory.getText().toString();
        int availableItems = 0;
        double price = 0.0;

        try {
            availableItems = Integer.parseInt(editTextAvailableItems.getText().toString());
            price = Double.parseDouble(editTextPrice.getText().toString());
        } catch (NumberFormatException e) {
            // Handle the exception, e.g., show a toast or error message
            return;
        }

        dbHelper.addMenuItem(name, category, availableItems, price);
        loadMenuItems();
    }

    private void updateMenuItem() {
        if (selectedItemId != -1) {
            String name = editTextItemName.getText().toString();
            String category = editTextCategory.getText().toString();
            int availableItems = 0;
            double price = 0.0;

            try {
                availableItems = Integer.parseInt(editTextAvailableItems.getText().toString());
                price = Double.parseDouble(editTextPrice.getText().toString());
            } catch (NumberFormatException e) {
                // Handle the exception, e.g., show a toast or error message
                return;
            }

            dbHelper.updateMenuItem(selectedItemId, name, category, availableItems, price);
            loadMenuItems();
        }
    }

    private void loadMenuItems() {
        menuItemsList.clear();
        Cursor cursor = dbHelper.getMenuItems();

        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    int idColumnIndex = cursor.getColumnIndex("id");
                    int nameColumnIndex = cursor.getColumnIndex("name");
                    int categoryColumnIndex = cursor.getColumnIndex("category");
                    int availableItemsColumnIndex = cursor.getColumnIndex("available_items");
                    int priceColumnIndex = cursor.getColumnIndex("price");

                    // Ensure the column indices are valid
                    if (idColumnIndex != -1 && nameColumnIndex != -1 && categoryColumnIndex != -1 && availableItemsColumnIndex != -1 && priceColumnIndex != -1) {
                        int id = cursor.getInt(idColumnIndex);
                        String name = cursor.getString(nameColumnIndex);
                        String category = cursor.getString(categoryColumnIndex);
                        int availableItems = cursor.getInt(availableItemsColumnIndex);
                        double price = cursor.getDouble(priceColumnIndex);

                        String item = id + " - " + name + " - " + category + " - " + availableItems + " - " + price;
                        menuItemsList.add(item);
                    } else {
                        // Handle case where one or more columns are missing
                        Log.e("AdminMenuActivity", "One or more columns are missing in the cursor.");
                    }
                } while (cursor.moveToNext());
            } catch (Exception e) {
                // Handle potential exceptions
                Log.e("AdminMenuActivity", "Error processing cursor: " + e.getMessage());
            } finally {
                cursor.close();
            }
        } else {
            // Handle case where cursor is null or empty
            Log.e("AdminMenuActivity", "Cursor is null or empty.");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItemsList);
        listViewMenuItems.setAdapter(adapter);
    }


    private int getItemIdFromDetails(String[] itemDetails) {
        // Extract and return the item ID
        if (itemDetails.length > 0) {
            try {
                return Integer.parseInt(itemDetails[0]);
            } catch (NumberFormatException e) {
                // Handle exception, e.g., return -1 or show an error
            }
        }
        return -1;
    }
}
