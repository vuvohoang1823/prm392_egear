package com.example.egear.customer.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.Cart;
import com.example.egear.room.CartDAO;
import com.example.egear.tabs.HomeFragment;

import java.util.List;

public class ProductDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "cart").allowMainThreadQueries().build();

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        ImageView imageView = findViewById(R.id.productImage);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productCategory = findViewById(R.id.productCategory);
//        TextView productStockQuantity = findViewById(R.id.product_stock_quantity);
        TextView productDescription = findViewById(R.id.productDescription);
        ImageView backButton = (ImageView) findViewById(R.id.btnBack);

        Glide.with(this).load(product.getImageUrl()).into(imageView);
        productName.setText(product.getName());
        productPrice.setText(String.format("%s $", product.getPrice()));
        productCategory.setText(product.getCategory());
//        productStockQuantity.setText(String.valueOf(product.getStockQuantity()));
        productDescription.setText(product.getDescription());

        CartDAO cartDAO = db.getCartDAO();
        Button addToCart = (Button) findViewById(R.id.btnAddToCart);

        List<Cart> carts = cartDAO.getCarts();
        for (Cart cart : carts) {
            if (cart.getId().equals(product.getId())) {
                addToCart.setEnabled(false);
            }
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDAO.insert(new Cart(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getCategory(), 1));

                List<Cart> carts = cartDAO.getCarts();
                System.out.println(carts);
                addToCart.setEnabled(false);
                Toast.makeText(ProductDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                finish();
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
}
