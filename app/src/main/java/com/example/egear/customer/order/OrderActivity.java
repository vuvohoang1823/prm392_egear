package com.example.egear.customer.order;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.cart.ComboCart;
import com.example.egear.customer.cart.UnifiedAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrder;
    private UnifiedAdapter unifiedAdapter;
    private Button buttonConfirmOrder;
    private TextView totalPriceTextView;
    private ArrayList<Cart> selectedProducts;
    private ArrayList<ComboCart> selectedCombos;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerViewOrder = findViewById(R.id.recycler_view_order);
        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(this));

        buttonConfirmOrder = findViewById(R.id.button_confirm_order);
        totalPriceTextView = findViewById(R.id.total_price);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        // Get the selected items from the intent
        selectedProducts = (ArrayList<Cart>) getIntent().getSerializableExtra("selected_products");
        selectedCombos = (ArrayList<ComboCart>) getIntent().getSerializableExtra("selected_combos");
        List<Object> selectedItems = new ArrayList<>();
        selectedItems.addAll(selectedProducts);
        selectedItems.addAll(selectedCombos);

        unifiedAdapter = new UnifiedAdapter(this, selectedItems);
        recyclerViewOrder.setAdapter(unifiedAdapter);

        // Calculate and display the total price
        calculateTotalPrice();

        buttonConfirmOrder.setOnClickListener(v -> {
            Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();
            // Handle order confirmation logic here
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Cart item : selectedProducts) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        for (ComboCart item : selectedCombos) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        totalPriceTextView.setText("TOTAL: $" + String.format("%.2f", totalPrice));
    }
}
