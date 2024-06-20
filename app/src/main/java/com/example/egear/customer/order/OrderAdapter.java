package com.example.egear.customer.order;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.customer.cart.Cart;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
   private Context context;
   private List<Cart> orderItemList;

   public OrderAdapter(Context context, List<Cart> orderItemList) {
      this.context = context;
      this.orderItemList = orderItemList;
   }

   @NonNull
   @Override
   public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
      return new OrderViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
      Cart orderItem = orderItemList.get(position);
      Long totalPrice = orderItem.getPrice() * orderItem.getQuantity();
      holder.orderProductName.setText(orderItem.getName());
      holder.orderProductPrice.setText("$" + totalPrice);
      holder.orderProductQuantity.setText("Quantity: " + orderItem.getQuantity());
//      holder.orderProductImage.setImageResource(orderItem.getImage());
        Glide.with(holder.itemView.getContext()).load(orderItem.getImage()).into(holder.orderProductImage);
   }

   @Override
   public int getItemCount() {
      return orderItemList.size();
   }

   public static class OrderViewHolder extends RecyclerView.ViewHolder {
      ImageView orderProductImage;
      TextView orderProductName;
      TextView orderProductPrice;
      TextView orderProductQuantity;

      public OrderViewHolder(@NonNull View itemView) {
         super(itemView);
         orderProductImage = itemView.findViewById(R.id.order_product_image);
         orderProductName = itemView.findViewById(R.id.order_product_name);
         orderProductPrice = itemView.findViewById(R.id.order_product_price);
         orderProductQuantity = itemView.findViewById(R.id.order_product_quantity);
      }
   }
}

