package com.example.egear.admin.combo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboDetailResponse;
import com.example.egear.customer.combo.ComboService;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductAdapter;
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
    Button btnEdit, btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_detail_combo);
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
        btnEdit = findViewById(R.id.btnEditCombo);
        btnDelete = findViewById(R.id.btnDeleteCombo);

        comboName.setText(combo.getName());
        comboDescription.setText(combo.getDescription());
        comboPrice.setText(combo.getPrice() + " $");
        comboPercentDiscount.setText(combo.getPercentDiscount() + " OFF");
        comboValueDiscount.setText(combo.getValueDiscount().toString());
        Glide.with(this).load(combo.getImageUrl()).into(comboImage);

        recyclerView = findViewById(R.id.admin_related_products);
        getProductsInCombo(combo);
        adapter = new ProductAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        if (combo.getStatus().equals("DELETED")) {
            btnDelete.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddEditCombo.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(combo);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to the previous activity
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
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
//                System.out.println(response.body().getData().getProducts());
                products.addAll(response.body().getData().getProducts());
                combo.setProducts(products);
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

    private void deleteCombo(Combo combo) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ComboService comboService = retrofit.create(ComboService.class);
        Call<Void> call = comboService.deleteCombo("Bearer " + token, combo.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.body());
                    return;
                }
                Toast.makeText(ComboDetail.this, "Combo deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    private void showDeleteConfirmationDialog(Combo combo) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete Combo")
                .setMessage("Are you sure you want to delete this combo?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Thực hiện chức năng xóa sản phẩm
                        deleteCombo(combo);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
