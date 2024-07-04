package com.example.egear.admin.products;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.admin.combo.AddEditCombo;
import com.example.egear.admin.combo.ImageResponse;
import com.example.egear.admin.combo.ImageService;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditProduct extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    boolean isImageChanged = false;
    boolean editMode = false;
    Long productId;
    EditText name, description, price, quantity;
    Spinner category;
    ImageView btnAddImage;
    Button btnSave;
    Long imageId = null;
    Uri uri;
    AddProductRequest product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_edit_product);
        name = findViewById(R.id.productNameAdminAdd);
        description = findViewById(R.id.productDescriptionAdminAdd);
        price = findViewById(R.id.productPriceAdminAdd);
        category = findViewById(R.id.productCategoryAdminAddEdit);
        quantity = findViewById(R.id.productStockAdminAdd);
        btnAddImage = findViewById(R.id.productImageAdminAdd);
        btnSave = findViewById(R.id.btnConfirmProductAdd);
        btnAddImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        if (getIntent().hasExtra("product")) {
            Product product = (Product) getIntent().getSerializableExtra("product");
            productId = product.getId();
            name.setText(product.getName());
            description.setText(product.getDescription());
            price.setText(String.valueOf(product.getPrice()));
            ArrayAdapter<CharSequence> editAdapter = ArrayAdapter.createFromResource(this,
                    R.array.product_categories, android.R.layout.simple_spinner_item);
            editAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category.setAdapter(editAdapter);
            quantity.setText(String.valueOf(product.getQuantity()));
            Glide.with(this).load(product.getImageUrl()).into(btnAddImage);

            editMode = true;
            btnSave.setText("Update Product");
        }

        btnSave.setOnClickListener(v -> {
            if (editMode) {
                if (isImageChanged) {
                    product = new AddProductRequest(name.getText().toString(), description.getText().toString(),
                            Long.parseLong(price.getText().toString()), "ACTIVE", category.getSelectedItem().toString(),
                            Integer.parseInt(quantity.getText().toString()));
                    uploadImage(uri, imageId -> {
                        updateProduct();
                    });
                } else {
                    product = new AddProductRequest(name.getText().toString(), description.getText().toString(),
                            Long.parseLong(price.getText().toString()), "ACTIVE", category.getSelectedItem().toString(),
                            Integer.parseInt(quantity.getText().toString()));
                    updateProduct();
                }
            } else {
                if (name.getText().toString().isEmpty() || description.getText().toString().isEmpty() || price.getText().toString().isEmpty() || quantity.getText().toString().isEmpty() || !isImageChanged) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                product = new AddProductRequest(name.getText().toString(), description.getText().toString(),
                        Long.parseLong(price.getText().toString()), "ACTIVE", category.getSelectedItem().toString(),
                        Integer.parseInt(quantity.getText().toString()));
                uploadImage(uri, imageId -> {
                    Log.d("Product", product.toString());
                    addProduct();
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ImageView imageView = findViewById(R.id.productImageAdminAdd);
                imageView.setImageBitmap(bitmap);
                isImageChanged = true; // Set isImageChanged to true when a new image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private interface UploadImageCallback {
        void onUploadFinished(Long imageId);
    }

    private void uploadImage(Uri uri, UploadImageCallback callback) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getCacheDir(), "tempFile");
            try (OutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;

                while ((read = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }
                output.flush();
            }

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
            List<MultipartBody.Part> files = new ArrayList<>();
            files.add(body);

            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            String token = sharedPreferences.getString("accessToken", "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:9999/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ImageService imageService = retrofit.create(ImageService.class);
            Call<ImageResponse> call = imageService.uploadImage("Bearer " + token, files);
            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Code: " + response.body());
                        return;
                    }
                    imageId = response.body().getData().get(0).getId();
                    product.setMain_media_id(imageId);
                    callback.onUploadFinished(imageId);
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    System.out.println("Error: " + t.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProduct() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<AddProductResponse> call = productService.addProduct("Bearer " + token, product);
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.body());
                    return;
                }
                Toast.makeText(AddEditProduct.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    private void updateProduct() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<AddProductResponse> call = productService.updateProduct("Bearer " + token, productId, product);
        call.enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(Call<AddProductResponse> call, Response<AddProductResponse> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.body());
                    return;
                }
                Toast.makeText(AddEditProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<AddProductResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}
