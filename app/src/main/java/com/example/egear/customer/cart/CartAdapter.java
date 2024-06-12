package com.example.egear.customer.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
   private Context context;
   private List<Cart> cartItemList;

   public CartAdapter(Context context, List<Cart> cartItemList) {
      this.context = context;
      this.cartItemList = cartItemList;
   }

   @NonNull
   @Override
   public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
      return new CartViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
      Cart cart = cartItemList.get(position);
      holder.productName.setText(cart.getName());
      holder.productPrice.setText("$" + cart.getPrice());
      holder.productQuantity.setText(String.valueOf(cart.getQuantity()));
      holder.productImage.setImageResource(cart.getImageResource());

      holder.buttonIncrease.setOnClickListener(v -> {
         int quantity = cart.getQuantity();
         cart.setQuantity(quantity + 1);
         notifyItemChanged(position);
      });

      holder.buttonDecrease.setOnClickListener(v -> {
         int quantity = cart.getQuantity();
         if (quantity > 1) {
            cart.setQuantity(quantity - 1);
            notifyItemChanged(position);
         }
      });

      holder.buttonRemove.setOnClickListener(v -> {
         cartItemList.remove(position);
         notifyItemRemoved(position);
         notifyItemRangeChanged(position, cartItemList.size());
      });
   }

   @Override
   public int getItemCount() {
      return cartItemList.size();
   }

   public static class CartViewHolder extends RecyclerView.ViewHolder {
      ImageView productImage;
      TextView productName;
      TextView productPrice;
      TextView productQuantity;
      Button buttonIncrease;
      Button buttonDecrease;
      Button buttonRemove;

      public CartViewHolder(@NonNull View itemView) {
         super(itemView);
         productImage = itemView.findViewById(R.id.product_image);
         productName = itemView.findViewById(R.id.product_name);
         productPrice = itemView.findViewById(R.id.product_price);
         productQuantity = itemView.findViewById(R.id.product_quantity);
         buttonIncrease = itemView.findViewById(R.id.button_increase);
         buttonDecrease = itemView.findViewById(R.id.button_decrease);
         buttonRemove = itemView.findViewById(R.id.button_remove);
      }
   }
}
