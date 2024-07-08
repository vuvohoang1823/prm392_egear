package com.example.egear.admin.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<OrderHistory> orderHistories;

    public OrderAdapter(Context context, List<OrderHistory> orderHistories) {
        this.context = context;
        this.orderHistories = orderHistories;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderHistory orderHistory = orderHistories.get(position);
        holder.orderId.setText("ID: " + orderHistory.getId());
        holder.orderTotal.setText("Total: $" + orderHistory.getNet_amount());
        holder.paymentMethod.setText("Method: " + orderHistory.getPayment_method());
        holder.orderStatus.setText(orderHistory.getStatus());

        if(orderHistory.getStatus().equals("COMPLETED")) {
            holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        } else if(orderHistory.getStatus().equals("CANCELLED")) {
            holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        } else {
            holder.orderStatus.setTextColor(holder.itemView.getResources().getColor(R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderTotal,paymentMethod, orderStatus;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderTotal = itemView.findViewById(R.id.order_total);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
