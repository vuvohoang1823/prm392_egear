package com.example.egear.admin.combo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductResponse;
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

public class AddEditCombo extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    boolean isImageChanged = false;
    boolean editMode = false;
    Long comboId;
    EditText name, description, percentDiscount, valueDiscount;
    TextView totalPrice;
    ImageView btnAddImage;
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    Button btnSave;
    List<Product> selectedItems = new ArrayList<>();
    Uri uri;
    String imageUrl = "";
    Combo combo;

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

        if (getIntent().hasExtra("combo")) {
            Combo combo = (Combo) getIntent().getSerializableExtra("combo");
            comboId = combo.getId();
            name.setText(combo.getName());
            description.setText(combo.getDescription());
            percentDiscount.setText(String.valueOf(combo.getPercentDiscount()));
            valueDiscount.setText(String.valueOf(combo.getValueDiscount()));
            totalPrice.setText(String.valueOf(combo.getPrice()));
            Glide.with(this).load(combo.getImageUrl()).into(btnAddImage);
            selectedItems = combo.getProducts();

            editMode = true;
            btnSave.setText("Update Combo");
        }

        // check all input fields and selected items
        btnSave.setOnClickListener(v -> {
            if (editMode) {
                if (isImageChanged) {
                    Toast.makeText(this, "Update Combo", Toast.LENGTH_SHORT).show();
                    combo = new Combo(name.getText().toString(), description.getText().toString(), percentDiscount.getText().toString(), valueDiscount.getText().toString(), selectedItems);
                    // upload new image
                    uploadImage(uri, imageUrl -> {
                        Log.d("Combo", combo.toString());
                    });
                } else {
                    Toast.makeText(this, "Update Combo", Toast.LENGTH_SHORT).show();
                    Combo combo = new Combo(name.getText().toString(), description.getText().toString(), percentDiscount.getText().toString(), valueDiscount.getText().toString(), selectedItems);
                    Log.d("Combo", combo.toString());
                }
            } else {
                if (name.getText().toString().isEmpty() || description.getText().toString().isEmpty() || percentDiscount.getText().toString().isEmpty() || valueDiscount.getText().toString().isEmpty() || selectedItems.isEmpty() || !isImageChanged) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Add Combo", Toast.LENGTH_SHORT).show();
                    combo = new Combo(name.getText().toString(), description.getText().toString(), percentDiscount.getText().toString(), valueDiscount.getText().toString(), selectedItems);
                    // upload new image
                    uploadImage(uri, imageUrl -> {
                        Log.d("Combo", combo.toString());
                    });
                }
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
                ImageView imageView = findViewById(R.id.input_combo_image);
                imageView.setImageBitmap(bitmap);
                isImageChanged = true; // Set isImageChanged to true when a new image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private interface UploadImageCallback {
        void onUploadFinished(String imageUrl);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
                    Log.d("Image", response.body().getData().get(0).getMedia_url());
                    imageUrl = response.body().getData().get(0).getMedia_url();
                    combo.setImageUrl(imageUrl);
                    callback.onUploadFinished(imageUrl);
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
}
