package com.example.egear.admin.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.products.Product;

public class ProductEditActivity extends AppCompatActivity {

   private ImageView productImageAdminEdit;
   private Spinner productCategoryAdminEdit;
   private EditText productNameAdminEdit;
   private EditText productPriceAdminEdit;
   private EditText productDescriptionAdminEdit;
   private Product product;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.admin_edit_product); // replace with your actual layout file name

      productImageAdminEdit = findViewById(R.id.productImageAdminEdit);
      productCategoryAdminEdit = findViewById(R.id.productCategoryAdminEdit);
      productNameAdminEdit = findViewById(R.id.productNameAdminEdit);
      productPriceAdminEdit = findViewById(R.id.productPriceAdminEdit);
      productDescriptionAdminEdit = findViewById(R.id.productDescriptionAdminEdit);
      ImageView backButton = findViewById(R.id.btnBackAdminEdit);

      // Get the product object from the intent
      Intent intent = getIntent();
      product = (Product) intent.getSerializableExtra("product");

      // Set up Spinner with categories
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.product_categories, android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      productCategoryAdminEdit.setAdapter(adapter);

      // Populate the fields with the product data
      if (product != null) {
         Glide.with(this).load(product.getImageUrl()).into(productImageAdminEdit);
         productNameAdminEdit.setText(product.getName());
         productPriceAdminEdit.setText(String.valueOf(product.getPrice()));
         productDescriptionAdminEdit.setText(product.getDescription());

         // Set the spinner selection based on product category
         int spinnerPosition = adapter.getPosition(product.getCategory());
         productCategoryAdminEdit.setSelection(spinnerPosition);
      }

      productCategoryAdminEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // Handle item selection
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
            // Handle no item selected
         }
      });

      productImageAdminEdit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Handle image click for image selection
            Toast.makeText(ProductEditActivity.this, "Choose Image To Upload", Toast.LENGTH_SHORT).show();
         }
      });

      backButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Go back to the previous activity
            finish();
         }
      });
   }
}
