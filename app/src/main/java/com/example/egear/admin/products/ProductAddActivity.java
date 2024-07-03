package com.example.egear.admin.products;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.egear.R;

public class ProductAddActivity extends AppCompatActivity {

   private ImageView productImageAdminAdd;
   private Spinner productCategoryAdminAdd;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.admin_add_product); // replace with your actual layout file name

      productImageAdminAdd = findViewById(R.id.productImageAdminAdd);
      productCategoryAdminAdd = findViewById(R.id.productCategoryAdminAdd);
      ImageView backButton = (ImageView) findViewById(R.id.btnBackAdminAdd);

      // Set up Spinner with categories
      ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
              R.array.product_categories, android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      productCategoryAdminAdd.setAdapter(adapter);

      productCategoryAdminAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            // Handle item selection
         }

         @Override
         public void onNothingSelected(AdapterView<?> parentView) {
            // Handle no item selected
         }
      });

      productImageAdminAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // Handle image click for image selection
            Toast.makeText(ProductAddActivity.this, "Choose Image To Upload", Toast.LENGTH_SHORT).show();
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