package com.example.app3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemQuantityChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<PizzaItem> pizzaItemList = new ArrayList<>();
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);

        // Add sample items to the cart
        pizzaItemList.add(new PizzaItem("Pizza Margherita", 6599));
        pizzaItemList.add(new PizzaItem("Pepperoni Pizza", 2599));
        pizzaItemList.add(new PizzaItem("Chicago Deep-Dish Pizza", 6999));
        pizzaItemList.add(new PizzaItem("Devilled Chicken Pizza", 6999));
        pizzaItemList.add(new PizzaItem("Beef Steak Pizza", 5999));

        cartAdapter = new CartAdapter(pizzaItemList, this);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateTotalPrice();
    }

    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (PizzaItem item : pizzaItemList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        totalPriceTextView.setText("Total: Rs " + totalPrice);
    }
}
