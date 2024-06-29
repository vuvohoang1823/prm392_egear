package com.example.egear.customer.cart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.ComboDatabase;

import java.util.ArrayList;
import java.util.List;

public class UnifiedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int CART = 0, COMBO = 1;
    private Context context;
    private List<Object> itemList = new ArrayList<>();
    private List<Cart> selectedProductItems = new ArrayList<>();
    private List<ComboCart> selectedComboItems = new ArrayList<>();
    private AppDatabase db;
    private ComboDatabase comboDb;
    private boolean isInOrder;

    public UnifiedAdapter(Context context, List<Object> itemList, boolean isInOrder) {
        this.context = context;
        this.itemList = itemList;
        this.isInOrder = isInOrder;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Cart) {
            return CART;
        } else if (itemList.get(position) instanceof ComboCart) {
            return COMBO;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (isInOrder) {
            if (viewType == CART) {
                view = inflater.inflate(R.layout.order_item, parent, false);
                return new CartAdapter.CartViewHolder(view);
            } else {
                view = inflater.inflate(R.layout.order_combo, parent, false);
                return new ComboCartAdapter.ComboCartViewHolder(view);
            }
        } else {
            if (viewType == CART) {
                view = inflater.inflate(R.layout.cart_item, parent, false);
                return new CartAdapter.CartViewHolder(view);
            } else {
                view = inflater.inflate(R.layout.cart_combo_item, parent, false);
                return new ComboCartAdapter.ComboCartViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(isInOrder) {
            if (holder.getItemViewType() == CART) {
                CartAdapter.CartViewHolder cartViewHolder = (CartAdapter.CartViewHolder) holder;
                Cart cart = (Cart) itemList.get(position);
                // bind cart data to cartViewHolder
                cartViewHolder.productName.setText(cart.getName());
                cartViewHolder.productPrice.setText("$" + (cart.getPrice()));
                cartViewHolder.productQuantity.setText("Quantity" + cart.getQuantity());
                Glide.with(cartViewHolder.itemView.getContext()).load(cart.getImage()).into(cartViewHolder.productImage);
            } else if (holder.getItemViewType() == COMBO) {
                ComboCart comboCart = (ComboCart) itemList.get(position);
                ComboCartAdapter.ComboCartViewHolder comboCartViewHolder = (ComboCartAdapter.ComboCartViewHolder) holder;
                // bind combo data to comboViewHolder
                comboCartViewHolder.comboName.setText(comboCart.getName());
                comboCartViewHolder.comboPrice.setText("$" + (comboCart.getPrice()));
                comboCartViewHolder.comboQuantity.setText("Quantity" + comboCart.getQuantity());
                Glide.with(comboCartViewHolder.itemView.getContext()).load(comboCart.getImageUrl()).into(comboCartViewHolder.comboImage);
            }
        } else {
            if (holder.getItemViewType() == CART) {
                CartAdapter.CartViewHolder cartViewHolder = (CartAdapter.CartViewHolder) holder;
                Cart cart = (Cart) itemList.get(position);
                // bind cart data to cartViewHolder
                cartViewHolder.productName.setText(cart.getName());
                cartViewHolder.productPrice.setText("$" + (cart.getPrice()));
                cartViewHolder.productQuantity.setText(String.valueOf(cart.getQuantity()));
                Glide.with(cartViewHolder.itemView.getContext()).load(cart.getImage()).into(cartViewHolder.productImage);
                cartViewHolder.productCheckbox.setChecked(selectedProductItems.contains(cart));

                cartViewHolder.productCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedProductItems.add(cart);
                    } else {
                        selectedProductItems.remove(cart);
                    }
                });

                cartViewHolder.buttonIncrease.setOnClickListener(v -> {
                    int quantity = cart.getQuantity();
                    cart.setQuantity(quantity + 1);
                    notifyItemChanged(position);
                });

                cartViewHolder.buttonDecrease.setOnClickListener(v -> {
                    int quantity = cart.getQuantity();
                    if (quantity > 1) {
                        cart.setQuantity(quantity - 1);
                        notifyItemChanged(position);
                    }
                });

                cartViewHolder.buttonRemove.setOnClickListener(v -> {
                    itemList.remove(position);
                    selectedProductItems.remove(cart);
                    db = Room.databaseBuilder(cartViewHolder.itemView.getContext(), AppDatabase.class, "cart").allowMainThreadQueries().build();
                    com.example.egear.room.Cart cart1 = db.getCartDAO().findCartByName(cart.getName());
                    if (cart1 != null) {
                        db.getCartDAO().delete(cart1);
                    }

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, itemList.size());
                });
            } else if (holder.getItemViewType() == COMBO) {
                ComboCartAdapter.ComboCartViewHolder comboCartViewHolder = (ComboCartAdapter.ComboCartViewHolder) holder;
                ComboCart comboCart = (ComboCart) itemList.get(position);
                // bind combo data to comboViewHolder
                comboCartViewHolder.comboName.setText(comboCart.getName());
                comboCartViewHolder.comboPrice.setText("$" + (comboCart.getPrice()));
                comboCartViewHolder.comboQuantity.setText(String.valueOf(comboCart.getQuantity()));
                Glide.with(comboCartViewHolder.itemView.getContext()).load(comboCart.getImageUrl()).into(comboCartViewHolder.comboImage);
                comboCartViewHolder.comboCheckbox.setChecked(selectedComboItems.contains(comboCart));

                comboCartViewHolder.buttonIncrease.setOnClickListener(v -> {
                    int quantity = comboCart.getQuantity();
                    comboCart.setQuantity(quantity + 1);
                    notifyItemChanged(position);
                });

                comboCartViewHolder.buttonDecrease.setOnClickListener(v -> {
                    int quantity = comboCart.getQuantity();
                    if (quantity > 1) {
                        comboCart.setQuantity(quantity - 1);
                        notifyItemChanged(position);
                    }
                });

                comboCartViewHolder.comboCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        selectedComboItems.add(comboCart);
                    } else {
                        selectedComboItems.remove(comboCart);
                    }
                });

                comboCartViewHolder.buttonRemove.setOnClickListener(v -> {
                    itemList.remove(position);
                    selectedComboItems.remove(comboCart);
                    comboDb = Room.databaseBuilder(comboCartViewHolder.itemView.getContext(), ComboDatabase.class, "combo").allowMainThreadQueries().build();
                    com.example.egear.room.Combo comboDbItem = comboDb.getComboDAO().getComboById(comboCart.getId());
                    if (comboDbItem != null) {
                        comboDb.getComboDAO().delete(comboDbItem);
                    }

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, itemList.size());
                });
            }
        }
    }

    public List<Cart> getSelectedProductItems() {
        return selectedProductItems;
    }

    public List<ComboCart> getSelectedComboItems() {
        return selectedComboItems;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
