package com.example.egear.customer.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>
{
    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);

        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
        ProductAdapter.ProductViewHolder viewHolder = new ProductAdapter.ProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productCategory.setText(product.getCategory());
//        holder.productStockQuantity.setText(String.valueOf(product.getStockQuantity()));
//        holder.productImage.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productCategory, productStockQuantity;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            productImage = itemView.findViewById(R.id.imageViewImage);
            productName = itemView.findViewById(R.id.textViewName);
            productPrice = itemView.findViewById(R.id.textViewPrice);
            productCategory = itemView.findViewById(R.id.textViewCategory);
//            productStockQuantity = itemView.findViewById(R.id.product_stock_quantity);
        }
    }
}
