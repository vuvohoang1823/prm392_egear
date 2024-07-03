package com.example.egear.customer.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.egear.MainActivity;
import com.example.egear.R;
import com.example.egear.auth.Login;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.cart.ComboCart;
import com.example.egear.customer.cart.UnifiedAdapter;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.CartDAO;
import com.example.egear.room.ComboDAO;
import com.example.egear.room.ComboDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrder;
    private UnifiedAdapter unifiedAdapter;
    private Button buttonConfirmOrder;
    private TextView totalPriceTextView;
    private ArrayList<Cart> selectedProducts;
    private ArrayList<ComboCart> selectedCombos;
    private ImageView btnBack;
    private AppDatabase db;
    private ComboDatabase comboDatabase;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        db = Room.databaseBuilder(this, AppDatabase.class, "cart").allowMainThreadQueries().build();
        comboDatabase = Room.databaseBuilder(this, ComboDatabase.class, "combo").allowMainThreadQueries().build();

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

        unifiedAdapter = new UnifiedAdapter(this, selectedItems, true);
        recyclerViewOrder.setAdapter(unifiedAdapter);

        // Calculate and display the total price
        calculateTotalPrice();

        buttonConfirmOrder.setOnClickListener(v -> {
//            Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();
            // Handle order confirmation logic here
//             c1
//            Intent intent = new Intent(this, Purchase.class);
//            startActivity(intent);
//             c2
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("https://google.com"));
//            startActivity(intent);
            order();
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

    private void order() {
        List<Item> comboItemList = new ArrayList<>();
        List<OrderItem> customerOrderItemList = new ArrayList<>();
        for (Cart cart : selectedProducts) {
            customerOrderItemList.add(new OrderItem(cart.getQuantity(), cart.getId()));
        }
        for (ComboCart comboCart : selectedCombos) {
            comboItemList.add(new Item(comboCart.getId(), comboCart.getQuantity()));
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginPrefs", this.MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        String accountId = sharedPreferences.getString("accountId", "");

        // Create an order object
        Order order = new Order("Order test", "112/43 Chiến Thắng, P9, Phú Nhuận, TP.Hồ Chí Minh", Long.parseLong(accountId), "COD", comboItemList, customerOrderItemList);

//         Send the order to the server
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OrderService orderService = retrofit.create(OrderService.class);
        Log.d("Order", order.toString());
        Call<OrderResponse> call = orderService.createOrder("Bearer " + token, order);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    // clear cart in room
                    clearCart();
                    Toast.makeText(OrderActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    if (response.code() == 400) {
                        OrderResponse orderResponse = response.body();
                        Log.d("Order", orderResponse.getMessage());
                        Toast.makeText(OrderActivity.this, orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("Order", response.toString());
                    Toast.makeText(OrderActivity.this, "Order Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.d("Eror", t.getMessage());
                Toast.makeText(OrderActivity.this, "Order Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearCart() {
        // Clear the cart in the local database
        CartDAO cartDAO = db.getCartDAO();
        ComboDAO comboDAO = comboDatabase.getComboDAO();
        if (!selectedProducts.isEmpty()) {
            for (Cart cart : selectedProducts) {
                com.example.egear.room.Cart cartItem = new com.example.egear.room.Cart(cart.getId(), cart.getName(), cart.getPrice(), cart.getImage(), cart.getCategory(), cart.getQuantity());
                cartDAO.delete(cartItem);
            }
        }
        if (!selectedCombos.isEmpty()) {
            for (ComboCart comboCart : selectedCombos) {
                com.example.egear.room.Combo comboCartItem = new com.example.egear.room.Combo(comboCart.getId(), comboCart.getName(), comboCart.getDescription(), comboCart.getPrice(), comboCart.getImageUrl(), comboCart.getPercentDiscount(), comboCart.getValueDiscount(), comboCart.getQuantity());
                comboDAO.delete(comboCartItem);
            }
        }
    }
}
