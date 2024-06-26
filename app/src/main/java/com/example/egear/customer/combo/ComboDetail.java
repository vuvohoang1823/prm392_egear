package com.example.egear.customer.combo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductAdapter;
import com.example.egear.customer.products.ProductDetail;
import com.example.egear.customer.products.ProductResponse;
import com.example.egear.customer.products.ProductService;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.ComboDAO;
import com.example.egear.room.ComboDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComboDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_combo);
        ComboDatabase db = Room.databaseBuilder(this, ComboDatabase.class, "combo").allowMainThreadQueries().build();

        Intent intent = getIntent();
        Combo combo = (Combo) intent.getSerializableExtra("combo");
        TextView comboName = findViewById(R.id.comboName);
        TextView comboDescription = findViewById(R.id.comboDescription);
        TextView comboPrice = findViewById(R.id.comboPrice);
        TextView comboPercentDiscount = findViewById(R.id.comboPercentDiscount);
        TextView comboValueDiscount = findViewById(R.id.comboValueDiscount);
        ImageView comboImage = findViewById(R.id.comboImage);
        ImageView backButton = (ImageView) findViewById(R.id.btnBackDetailCombo);

        comboName.setText(combo.getName());
        comboDescription.setText(combo.getDescription());
        comboPrice.setText(combo.getPrice() + " $");
        comboPercentDiscount.setText(combo.getPercentDiscount() + " OFF");
        comboValueDiscount.setText(combo.getValueDiscount().toString());
        Glide.with(this).load(combo.getImageUrl()).into(comboImage);

        recyclerView = findViewById(R.id.related_products);
        getProductsInCombo(combo);
        adapter = new ProductAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        ComboDAO comboDAO = db.getComboDAO();
        Button addToCart = (Button) findViewById(R.id.addToCart);
        List<com.example.egear.room.Combo> combos = comboDAO.getCombos();
        for (com.example.egear.room.Combo combo1 : combos) {
            if (combo1.getId().equals(combo.getId())) {
                addToCart.setEnabled(false);
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to the previous activity
                finish();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comboDAO.insert(new com.example.egear.room.Combo(combo.getId(), combo.getName(), combo.getDescription(), combo.getPrice(), combo.getImageUrl(), combo.getPercentDiscount(), combo.getValueDiscount(), 1));

                List<com.example.egear.room.Combo> combos = comboDAO.getCombos();
                System.out.println(combos);
                addToCart.setEnabled(false);
                Toast.makeText(ComboDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getProductsInCombo(Combo combo) {
        products = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComboService jsonPlaceholder = retrofit.create(ComboService.class);
        Call<ComboDetailResponse> call = jsonPlaceholder.getProductsInCombo("Bearer " + token, combo.getId());
        call.enqueue(new Callback<ComboDetailResponse>() {
            @Override
            public void onResponse(Call<ComboDetailResponse> call, Response<ComboDetailResponse> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
//                System.out.println(response.body().getData().getProducts());
                products.addAll(response.body().getData().getProducts());
                adapter = new ProductAdapter(products);
                // horizontal recyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ComboDetailResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}
