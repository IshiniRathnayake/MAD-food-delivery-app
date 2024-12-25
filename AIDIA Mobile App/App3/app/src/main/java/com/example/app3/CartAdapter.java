package com.example.app3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<PizzaItem> pizzaItemList;
    private OnItemQuantityChangeListener listener;

    public interface OnItemQuantityChangeListener {
        void onQuantityChanged();
    }

    public CartAdapter(List<PizzaItem> pizzaItemList, OnItemQuantityChangeListener listener) {
        this.pizzaItemList = pizzaItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        PizzaItem item = pizzaItemList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText("Rs " + item.getPrice());
        holder.quantityTextView.setText(String.valueOf(item.getQuantity()));

        // Handle Add (+) Button Click
        holder.addButton.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            listener.onQuantityChanged();
        });

        // Handle Remove (-) Button Click
        holder.removeButton.setOnClickListener(v -> {
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                listener.onQuantityChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, quantityTextView;
        Button addButton, removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            addButton = itemView.findViewById(R.id.addButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
