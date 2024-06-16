package com.example.egear.customer.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
   private Context context;
   private List<Cart> cartItemList;
   private List<Cart> selectedItems = new ArrayList<>();

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
      Cart cartItem = cartItemList.get(position);
      holder.productName.setText(cartItem.getName());
      holder.productPrice.setText("$" + cartItem.getPrice());
      holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
      holder.productImage.setImageResource(cartItem.getImageResource());
      holder.productCheckbox.setChecked(selectedItems.contains(cartItem));

      holder.productCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
         if (isChecked) {
            selectedItems.add(cartItem);
         } else {
            selectedItems.remove(cartItem);
         }
      });

      holder.buttonIncrease.setOnClickListener(v -> {
         int quantity = cartItem.getQuantity();
         cartItem.setQuantity(quantity + 1);
         notifyItemChanged(position);
      });

      holder.buttonDecrease.setOnClickListener(v -> {
         int quantity = cartItem.getQuantity();
         if (quantity > 1) {
            cartItem.setQuantity(quantity - 1);
            notifyItemChanged(position);
         }
      });

      holder.buttonRemove.setOnClickListener(v -> {
         cartItemList.remove(position);
         selectedItems.remove(cartItem);
         notifyItemRemoved(position);
         notifyItemRangeChanged(position, cartItemList.size());
      });
   }

   @Override
   public int getItemCount() {
      return cartItemList.size();
   }

   public List<Cart> getSelectedItems() {
      return selectedItems;
   }

   public static class CartViewHolder extends RecyclerView.ViewHolder {
      CheckBox productCheckbox;
      ImageView productImage;
      TextView productName;
      TextView productPrice;
      TextView productQuantity;
      Button buttonIncrease;
      Button buttonDecrease;
      Button buttonRemove;

      public CartViewHolder(@NonNull View itemView) {
         super(itemView);
         productCheckbox = itemView.findViewById(R.id.product_checkbox);
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