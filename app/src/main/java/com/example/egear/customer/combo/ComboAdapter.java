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

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {
    private List<Combo> combos;
    private OnItemClickListener listener;

    public ComboAdapter(List<Combo> combos) {
        this.combos = combos;
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.row_combo, parent, false);
        ComboAdapter.ComboViewHolder viewHolder = new ComboAdapter.ComboViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        Combo combo = combos.get(position);
        holder.comboName.setText(combo.getName());
        holder.comboDescription.setText(combo.getDescription());
        holder.comboPrice.setText(combo.getPrice() + " $");
        holder.comboPercentDiscount.setText(combo.getPercentDiscount() + " %");
//        holder.comboImage.setImageResource(combo.getImage());

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
        TextView comboName, comboDescription, comboPrice, comboPercentDiscount;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
//            comboImage = itemView.findViewById(R.id.combo_image);
            comboName = itemView.findViewById(R.id.comboName);
            comboDescription = itemView.findViewById(R.id.comboDescription);
            comboPrice = itemView.findViewById(R.id.comboPrice);
            comboPercentDiscount = itemView.findViewById(R.id.percentDiscount);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Combo combo, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
