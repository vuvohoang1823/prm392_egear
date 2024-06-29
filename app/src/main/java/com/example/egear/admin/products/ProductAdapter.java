package com.example.egear.admin.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.products.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
   private List<Product> products;

   public ProductAdapter(List<Product> products) {
      this.products = products;
   }

   public interface OnItemClickListener {
      void onItemClick(Product product, int position);
   }

   private OnItemClickListener listener;

   public void setOnItemClickListener(OnItemClickListener listener) {
      this.listener = listener;
   }

   @NonNull
   @Override
   public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Context parentContext = parent.getContext();
      LayoutInflater layoutInflater = LayoutInflater.from(parentContext);

      View view = layoutInflater.inflate(R.layout.admin_row_product, parent, false);
      return new ProductViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
      Product product = products.get(position);
      holder.productName.setText(product.getName());
      holder.productPrice.setText(product.getPrice() + " $");
      holder.productCategory.setText(product.getCategory());
      Glide.with(holder.itemView.getContext()).load(product.getImageUrl()).into(holder.productImage);

      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (listener != null) {
               listener.onItemClick(product, holder.getAdapterPosition());
            }
         }
      });
   }

   @Override
   public int getItemCount() {
      return products.size();
   }

   public static class ProductViewHolder extends RecyclerView.ViewHolder {
      ImageView productImage;
      TextView productName, productPrice, productCategory;

      public ProductViewHolder(@NonNull View itemView) {
         super(itemView);
         productImage = itemView.findViewById(R.id.imageViewImageAdmin);
         productName = itemView.findViewById(R.id.textViewNameAdmin);
         productPrice = itemView.findViewById(R.id.textViewPriceAdmin);
         productCategory = itemView.findViewById(R.id.textViewCategoryAdmin);
      }
   }
}
