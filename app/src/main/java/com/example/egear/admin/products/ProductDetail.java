package com.example.egear.admin.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.products.Product;

public class ProductDetail extends AppCompatActivity {
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
      Button deleteButton = findViewById(R.id.btnDeleteProduct);

      editButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Pass the product object to ProductEditActivity
            Intent intent = new Intent(ProductDetail.this, ProductEditActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
         }
      });

      deleteButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Thực hiện chức năng xóa sản phẩm
         }
      });

      backButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            finish();
         }
      });
   }
}
