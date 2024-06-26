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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.room.ComboDatabase;

import java.util.ArrayList;
import java.util.List;

public class ComboCartAdapter extends RecyclerView.Adapter<ComboCartAdapter.ComboCartViewHolder> {
    private Context context;
    private List<ComboCart> combos;
    private List<ComboCart> selectedItems = new ArrayList<>();
    private ComboDatabase comboDb;

    public ComboCartAdapter(Context context, List<ComboCart> combos) {
        this.context = context;
        this.combos = combos;
    }

    @NonNull
    @Override
    public ComboCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_combo_item, parent, false);
        return new ComboCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboCartAdapter.ComboCartViewHolder holder, int position) {
        ComboCart combo = combos.get(position);
        holder.comboName.setText(combo.getName());
//        holder.comboDescription.setText(combo.getDescription());
//        holder.comboValueDiscount.setText(combo.getValueDiscount().toString());
        holder.comboPercentDiscount.setText(combo.getPercentDiscount() + " OFF");
        holder.comboPrice.setText(combo.getPrice().toString() + " $");
//        holder.comboImage.setImageResource(combo.getImage());
        Glide.with(holder.itemView.getContext()).load(combo.getImageUrl()).into(holder.comboImage);
        holder.comboQuantity.setText(String.valueOf(combo.getQuantity()));
        holder.comboCheckbox.setChecked(selectedItems.contains(combo));

        holder.buttonIncrease.setOnClickListener(v -> {
            int quantity = combo.getQuantity();
            combo.setQuantity(quantity + 1);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            int quantity = combo.getQuantity();
            if (quantity > 0) {
                combo.setQuantity(quantity - 1);
            }
        });

        holder.comboCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(combo);
            } else {
                selectedItems.remove(combo);
            }
        });

        holder.buttonRemove.setOnClickListener(v -> {
            combos.remove(position);
            selectedItems.remove(combo);

            comboDb = Room.databaseBuilder(context, ComboDatabase.class, "combo").allowMainThreadQueries().build();
            com.example.egear.room.Combo comboDbItem = comboDb.getComboDAO().getComboById(combo.getId());
            if (comboDbItem != null) {
                comboDb.getComboDAO().delete(comboDbItem);
            }

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, combos.size());
        });
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    public List<ComboCart> getSelectedItems() {
        return selectedItems;
    }

    public static class ComboCartViewHolder extends RecyclerView.ViewHolder {
        public CheckBox comboCheckbox;
        Button buttonIncrease;
        Button buttonDecrease;
        public Button buttonRemove;
        ImageView comboImage;
        TextView comboName, comboDescription, comboValueDiscount, comboPercentDiscount, comboPrice, comboQuantity;

        public ComboCartViewHolder(@NonNull View itemView) {
            super(itemView);
            comboImage = itemView.findViewById(R.id.combo_image);
            comboName = itemView.findViewById(R.id.combo_name);
//            comboDescription = itemView.findViewById(R.id.combo_description);
//            comboValueDiscount = itemView.findViewById(R.id.value_discount);
            comboPrice = itemView.findViewById(R.id.combo_price);
            comboPercentDiscount = itemView.findViewById(R.id.percent_discount);
            comboQuantity = itemView.findViewById(R.id.combo_quantity);
            comboCheckbox = itemView.findViewById(R.id.combo_checkbox);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }
}
