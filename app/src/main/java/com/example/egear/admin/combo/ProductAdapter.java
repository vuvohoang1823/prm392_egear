package com.example.egear.admin.combo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.products.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private List<Product> selectedItems;

    public ProductAdapter(List<Product> products, List<Product> selectedItems) {
        this.products = products;
        this.selectedItems = selectedItems;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product, int position);
    }

    private ProductAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);

        View view = layoutInflater.inflate(R.layout.admin_row_product_in_combo, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " $");
        holder.productCategory.setText(product.getCategory());
        Glide.with(holder.itemView.getContext()).load(product.getImageUrl()).into(holder.productImage);
        holder.productCheckbox.setChecked(selectedItems.contains(product));

        if(!selectedItems.isEmpty()) {
            selectedItems.forEach(item -> {
                if(item.getId().equals(product.getId())) {
                    holder.productCheckbox.setChecked(true);
                }
            });
        }

        holder.productCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(product);
            } else {
                selectedItems.remove(product);
            }
        });

//        Log.d("SelectedItems Adapter", String.valueOf(selectedItems.contains(product)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(product, holder.getAdapterPosition());
                }
            }
        });
    }

    public List<Product> getSelectedItems() {
        return selectedItems;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CheckBox productCheckbox;
        ImageView productImage;
        TextView productName, productPrice, productCategory;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productCheckbox = itemView.findViewById(R.id.admin_product_checkbox);
            productImage = itemView.findViewById(R.id.imageViewImageAdmin);
            productName = itemView.findViewById(R.id.textViewNameAdmin);
            productPrice = itemView.findViewById(R.id.textViewPriceAdmin);
            productCategory = itemView.findViewById(R.id.textViewCategoryAdmin);
        }
    }
}
