package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class FoodSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selection);

        // Pizza category
        LinearLayout pizzaLayout = findViewById(R.id.pizza3_layout);
        pizzaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Pizza selection activity
                Intent intent = new Intent(FoodSelectionActivity.this, ActivityPizzaSelection.class);
                startActivity(intent);
            }
        });

        // Repeat for other categories if needed
    }
}
