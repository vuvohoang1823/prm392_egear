package com.example.egear.customer.combo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;

public class ComboDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_combo);

        Intent intent = getIntent();
        Combo combo = (Combo) intent.getSerializableExtra("combo");
        TextView comboName = findViewById(R.id.comboName);
        TextView comboDescription = findViewById(R.id.comboDescription);
        TextView comboPrice = findViewById(R.id.comboPrice);
        TextView comboPercentDiscount = findViewById(R.id.comboPercentDiscount);
        ImageView backButton = (ImageView) findViewById(R.id.btnBack);

        comboName.setText(combo.getName());
        comboDescription.setText(combo.getDescription());
        comboPrice.setText(combo.getPrice() + " $");
        comboPercentDiscount.setText(combo.getPercentDiscount() + " %");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to the previous activity
                finish();
            }
        });
    }
}
