package com.example.egear.admin.combo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductDetail;
import com.example.egear.customer.products.ProductResponse;
import com.example.egear.customer.products.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditCombo extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    boolean isImageChanged = false;
    boolean isImageAdded = false;
    EditText name, description, percentDiscount, valueDiscount;
    TextView totalPrice;
    ImageView btnAddImage;
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    Button btnSave;
    List<Product> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_edit_combo);
        recyclerView = findViewById(R.id.product_combo_recycler_view);

        name = findViewById(R.id.input_combo_name);
        description = findViewById(R.id.input_combo_description);
        percentDiscount = findViewById(R.id.input_combo_percent_discount);
        valueDiscount = findViewById(R.id.input_combo_value_discount);
        totalPrice = findViewById(R.id.total_products_price);
        btnAddImage = findViewById(R.id.input_combo_image);
        btnSave = findViewById(R.id.addNewComboButton);

        getProducts();
        setAdapter(products);

        btnAddImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        });

        if(getIntent().hasExtra("combo")) {
            Combo combo = (Combo) getIntent().getSerializableExtra("combo");
            name.setText(combo.getName());
            description.setText(combo.getDescription());
            percentDiscount.setText(String.valueOf(combo.getPercentDiscount()));
            valueDiscount.setText(String.valueOf(combo.getValueDiscount()));
            totalPrice.setText(String.valueOf(combo.getPrice()));
            Glide.with(this).load(combo.getImageUrl()).into(btnAddImage);
        }

        // check all input fields and selected items
        btnSave.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty() || description.getText().toString().isEmpty() || percentDiscount.getText().toString().isEmpty() || valueDiscount.getText().toString().isEmpty() || selectedItems.isEmpty() || !isImageAdded) {
                Toast.makeText(AddEditCombo.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.input_combo_image);
                imageView.setImageBitmap(bitmap);
                isImageChanged = true; // Set isImageChanged to true when a new image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getProducts() {
        products = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductService jsonPlaceholder = retrofit.create(ProductService.class);
        Call<ProductResponse> call = jsonPlaceholder.getActiveProducts("Bearer " + token);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
//                System.out.println(response.body().getData());
                products.addAll(response.body().getData());
                setAdapter(products);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    private void setAdapter(List<Product> products) {
        adapter = new ProductAdapter(products, selectedItems);
        selectedItems = adapter.getSelectedItems();
        Log.d("SelectedItems", selectedItems.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
