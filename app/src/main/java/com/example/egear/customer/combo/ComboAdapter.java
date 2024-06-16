package com.example.egear.customer.combo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.R;
import com.example.egear.customer.products.Product;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<com.example.egear.customer.combo.ComboAdapter.ComboViewHolder>
{
    private List<Combo> combos;

    public ComboAdapter(List<Combo> combos) {
        this.combos = combos;
    }

    @NonNull
    @Override
    public com.example.egear.customer.combo.ComboAdapter.ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);

        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.row_product, parent, false);
        com.example.egear.customer.combo.ComboAdapter.ComboViewHolder viewHolder = new com.example.egear.customer.combo.ComboAdapter.ComboViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.egear.customer.combo.ComboAdapter.ComboViewHolder holder, int position) {
        Combo combo = combos.get(position);
        holder.comboName.setText(combo.getName());
        holder.comboValue_discount.setText(combo.getValue_discount());
        holder.comboPercent_discount.setText(combo.getPercent_discount());
//
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView comboName, comboValue_discount, comboPercent_discount;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
//            comboImage = itemView.findViewById(R.id.imageViewImage);
            comboName = itemView.findViewById(R.id.textViewName);
            comboValue_discount = itemView.findViewById(R.id.textViewValue_discount);
            comboPercent_discount = itemView.findViewById(R.id.textViewPercent_discount);
//
        }
    }
}
