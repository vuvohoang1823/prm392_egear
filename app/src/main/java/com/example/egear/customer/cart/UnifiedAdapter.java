package com.example.egear.customer.cart;

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
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboAdapter;
import com.example.egear.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class UnifiedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int CART = 0, COMBO = 1;
    private List<Object> itemList = new ArrayList<>();

    public UnifiedAdapter(List<Object> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Cart) {
            return CART;
        } else if (itemList.get(position) instanceof Combo) {
            return COMBO;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == CART) {
            view = inflater.inflate(R.layout.cart_item, parent, false);
            return new CartAdapter.CartViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.cart_combo_item, parent, false);
            return new ComboAdapter.ComboViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CART) {
            Cart cart = (Cart) itemList.get(position);
            CartAdapter.CartViewHolder cartViewHolder = (CartAdapter.CartViewHolder) holder;
            // bind cart data to cartViewHolder
        } else {
            Combo combo = (Combo) itemList.get(position);
            ComboAdapter.ComboViewHolder comboViewHolder = (ComboAdapter.ComboViewHolder) holder;
            // bind combo data to comboViewHolder
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
