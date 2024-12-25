package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityPizzaSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pizza);

        // Handle "Add to Cart" for Pizza Margherita
        ImageView addToCartPizza1 = findViewById(R.id.add_to_cart_pizza1);
        addToCartPizza1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCart("Pizza Margherita", 6599);
            }
        });

        // Handle "Add to Cart" for Pepperoni Pizza
        ImageView addToCartPizza2 = findViewById(R.id.add_to_cart_pizza2);
        addToCartPizza2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCart("Pepperoni Pizza", 2599);
            }
        });

        // Handle "Add to Cart" for Chicago Deep-Dish Pizza
        ImageView addToCartPizza3 = findViewById(R.id.add_to_cart_pizza3);
        addToCartPizza3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCart("Chicago Deep-Dish Pizza", 6999);
            }
        });

        // Handle "Add to Cart" for Devilled Chicken Pizza
        ImageView addToCartPizza4 = findViewById(R.id.add_to_cart_pizza4);
        addToCartPizza4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCart("Devilled Chicken Pizza", 6999);
            }
        });

        // Handle "Add to Cart" for Beef Steak Pizza
        ImageView addToCartPizza5 = findViewById(R.id.add_to_cart_pizza5);
        addToCartPizza5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCart("Beef Steak Pizza", 5999);
            }
        });
    }

    // Method to navigate to CartActivity with the selected pizza details
    private void navigateToCart(String pizzaName, int pizzaPrice) {
        Intent intent = new Intent(ActivityPizzaSelection.this, CartActivity.class);
        intent.putExtra("pizzaName", pizzaName);
        intent.putExtra("pizzaPrice", pizzaPrice);
        startActivity(intent);
    }
}
