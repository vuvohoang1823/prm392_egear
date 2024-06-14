package com.example.egear.customer.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egear.R;
import com.example.egear.tabs.HomeFragment;

public class ProductDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

//        ImageView imageView = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productCategory = findViewById(R.id.productCategory);
//        TextView productStockQuantity = findViewById(R.id.product_stock_quantity);
        TextView productDescription = findViewById(R.id.productDescription);
        ImageView backButton = (ImageView) findViewById(R.id.btnBack);

//        imageView.setImageResource(Integer.parseInt(product.getImage()));
        productName.setText(product.getName());
        productPrice.setText(String.format("%s $", product.getPrice()));
        productCategory.setText(product.getCategory());
//        productStockQuantity.setText(String.valueOf(product.getStockQuantity()));
        productDescription.setText(product.getDescription());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to the previous activity
                finish();
            }
        });
    }
}
