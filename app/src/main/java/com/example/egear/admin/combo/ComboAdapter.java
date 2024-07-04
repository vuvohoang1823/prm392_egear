package com.example.egear.admin.combo;

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
import com.example.egear.customer.combo.Combo;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<Combo> combos;
    private ComboAdapter.OnItemClickListener listener;

    public ComboAdapter(List<Combo> combos) {
        this.combos = combos;
    }

    @NonNull
    @Override
    public ComboAdapter.ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_combo, parent, false);
        ComboAdapter.ComboViewHolder viewHolder = new ComboAdapter.ComboViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComboAdapter.ComboViewHolder holder, int position) {
        Combo combo = combos.get(position);
        holder.comboName.setText(combo.getName());
        holder.comboPercentDiscount.setText(combo.getPercentDiscount() + " OFF");
        holder.comboPrice.setText(combo.getPrice().toString() + " $");
        Glide.with(holder.itemView.getContext()).load(combo.getImageUrl()).into(holder.comboImage);
        holder.comboStatus.setText(combo.getStatus());

        if (combo.getStatus().equals("ACTIVE")) {
            holder.comboStatus.setTextColor(holder.itemView.getResources().getColor(R.color.green));
        } else if (combo.getStatus().equals("DELETED")) {
            holder.comboStatus.setTextColor(holder.itemView.getResources().getColor(R.color.red));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(combo, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder {
        ImageView comboImage;
        TextView comboName, comboStatus, comboPercentDiscount, comboPrice;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            comboImage = itemView.findViewById(R.id.combo_image);
            comboName = itemView.findViewById(R.id.combo_name);
            comboPrice = itemView.findViewById(R.id.combo_price);
            comboPercentDiscount = itemView.findViewById(R.id.percent_discount);
            comboStatus = itemView.findViewById(R.id.combo_status);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Combo combo, int position);
    }

    public void setOnItemClickListener(ComboAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
