package com.example.egear.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.egear.customer.cart.ComboCart;
import com.example.egear.customer.cart.ComboCartAdapter;
import com.example.egear.customer.cart.UnifiedAdapter;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboAdapter;
import com.example.egear.customer.order.OrderActivity;
import com.example.egear.R;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.cart.CartAdapter;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.CartDAO;
import com.example.egear.room.ComboDAO;
import com.example.egear.room.ComboDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private ComboCartAdapter comboCartAdapter;
    private UnifiedAdapter unifiedAdapter;
    private List<Cart> cartItemList;
    private List<ComboCart> comboItemList;
    private Button buttonOrder;
    private AppDatabase db;
    private ComboDatabase comboDatabase;
    private LinearLayout emptyCartSection, cartTotalSection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

    private void init(View view) {
        recyclerViewCart = view.findViewById(R.id.recycler_view_cart);

        if (getActivity() != null) {
            db = Room.databaseBuilder(getActivity(), AppDatabase.class, "cart").allowMainThreadQueries().build();
            comboDatabase = Room.databaseBuilder(getActivity(), ComboDatabase.class, "combo").allowMainThreadQueries().build();
        }

        buttonOrder = view.findViewById(R.id.button_checkout);

        getProducts();
        getCombos();

//        cartAdapter = new CartAdapter(getContext(), cartItemList);
//        comboCartAdapter = new ComboCartAdapter(getContext(), comboItemList);
//        recyclerViewCart.setAdapter(cartAdapter);
        List<Object> itemList = new ArrayList<>();
        itemList.addAll(cartItemList);
        itemList.addAll(comboItemList);
        itemList.forEach(System.out::println);
        Log.d("itemList", itemList.size() + "");
        unifiedAdapter = new UnifiedAdapter(getContext(), itemList);
        System.out.println(unifiedAdapter.getItemCount());
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewCart.setAdapter(unifiedAdapter);

        if (cartItemList.isEmpty() && comboItemList.isEmpty()) {
            emptyCartSection.setVisibility(View.VISIBLE);
            cartTotalSection.setVisibility(View.GONE);
        } else {
            emptyCartSection.setVisibility(View.GONE);
            cartTotalSection.setVisibility(View.VISIBLE);
        }

        List<Cart> selectedProduct = unifiedAdapter.getSelectedProductItems();
        List<ComboCart> selectedCombo = unifiedAdapter.getSelectedComboItems();
//        List<Object> selectedItems = new ArrayList<>();
//        selectedItems.addAll(selectedProduct);
//        selectedItems.addAll(selectedCombo);

        buttonOrder.setOnClickListener(v -> {
            if (!selectedProduct.isEmpty() || !selectedCombo.isEmpty()) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("selected_products", new ArrayList<>(selectedProduct));
                intent.putExtra("selected_combos", new ArrayList<>(selectedCombo));
                startActivity(intent);
            }
        });
    }

    private void getProducts() {
        emptyCartSection = getView().findViewById(R.id.no_items_in_cart_section);
        cartTotalSection = getView().findViewById(R.id.cart_total_section);
        cartItemList = new ArrayList<>();
        CartDAO cartDAO = db.getCartDAO();
        List<com.example.egear.room.Cart> carts = cartDAO.getCarts();
        if (carts != null) {
            if (carts.isEmpty()) {
                recyclerViewCart.setVisibility(View.GONE);
                emptyCartSection.setVisibility(View.VISIBLE);
                cartTotalSection.setVisibility(View.GONE);
            } else {
                emptyCartSection.setVisibility(View.GONE);
                for (com.example.egear.room.Cart cart : carts) {
                    cartItemList.add(new Cart(cart.getId(), cart.getName(), cart.getPrice(), 1, cart.getImage()));
                }
            }
        }
    }

    private void getCombos() {
        emptyCartSection = getView().findViewById(R.id.no_items_in_cart_section);
        cartTotalSection = getView().findViewById(R.id.cart_total_section);
        comboItemList = new ArrayList<>();
        ComboDAO comboDAO = comboDatabase.getComboDAO();
        List<com.example.egear.room.Combo> combos = comboDAO.getCombos();
        if (combos != null) {
            if (combos.isEmpty()) {
                recyclerViewCart.setVisibility(View.GONE);
                emptyCartSection.setVisibility(View.VISIBLE);
                cartTotalSection.setVisibility(View.GONE);
            } else {
                emptyCartSection.setVisibility(View.GONE);
                for (com.example.egear.room.Combo combo : combos) {
                    comboItemList.add(new ComboCart(combo.getId(), combo.getName(), combo.getDescription(), combo.getProducts_total(), combo.getImg_url(), combo.getDiscount_by_percent(), combo.getDiscount_by_value(), combo.getQuantity()));
                }
            }
        }
    }
}
