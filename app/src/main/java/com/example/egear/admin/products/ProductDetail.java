package com.example.egear.admin.products;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ProductDetail extends AppCompatActivity {
   private Button deleteButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.admin_detail_product);

      Intent intent = getIntent();
      Product product = (Product) intent.getSerializableExtra("product");

      ImageView imageView = findViewById(R.id.productImageAdmin);
      TextView productName = findViewById(R.id.productNameAdmin);
      TextView productPrice = findViewById(R.id.productPriceAdmin);
      TextView productCategory = findViewById(R.id.productCategoryAdmin);
      TextView productDescription = findViewById(R.id.productDescriptionAdmin);
      ImageView backButton = findViewById(R.id.btnBackAdminEdit);

      Glide.with(this).load(product.getImageUrl()).into(imageView);
      productName.setText(product.getName());
      productPrice.setText(String.format("%s $", product.getPrice()));
      productCategory.setText(product.getCategory());
      productDescription.setText(product.getDescription());

      Button editButton = findViewById(R.id.btnEditProduct);
      deleteButton = findViewById(R.id.btnDeleteProduct);

      editButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Pass the product object to ProductEditActivity
            Intent intent = new Intent(ProductDetail.this, AddEditProduct.class);
            intent.putExtra("product", product);
            startActivity(intent);
         }
      });

      deleteButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Hiển thị popup xác nhận xóa
            showDeleteConfirmationDialog(product);
         }
      });

      backButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            finish();
         }
      });

      // Kiểm tra trạng thái sản phẩm và cập nhật hiển thị nút Delete
      if (product.getStatus().equals("DELETED")) {
         deleteButton.setVisibility(View.GONE);
      } else if (product.getStatus().equals("ACTIVE")) {
         deleteButton.setVisibility(View.VISIBLE);
      }
   }

   private void showDeleteConfirmationDialog(Product product) {
      new AlertDialog.Builder(this)
              .setIcon(android.R.drawable.ic_dialog_alert)
              .setTitle("Delete Product")
              .setMessage("Are you sure you want to delete this product?")
              .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    // Thực hiện chức năng xóa sản phẩm
                    deleteProduct(product);
                 }
              })
              .setNegativeButton("Cancel", null)
              .show();
   }

   private void deleteProduct(Product product) {
      SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
      String token = sharedPreferences.getString("accessToken", "");

      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("http://10.0.2.2:9999/api/v1/")
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      ProductService productService = retrofit.create(ProductService.class);
      Call<Void> call = productService.deleteProduct("Bearer " + token, product.getId());
      call.enqueue(new Callback<Void>() {
         @Override
         public void onResponse(Call<Void> call, Response<Void> response) {
            if (!response.isSuccessful()) {
               System.out.println("Code: " + response.body());
               return;
            }
            Toast.makeText(ProductDetail.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
         }

         @Override
         public void onFailure(Call<Void> call, Throwable t) {
            System.out.println("Error: " + t.getMessage());
         }
      });
   }
}
