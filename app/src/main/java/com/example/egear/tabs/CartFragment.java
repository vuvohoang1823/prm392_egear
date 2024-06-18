package com.example.egear.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.egear.R;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.cart.CartAdapter;
import com.example.egear.room.AppDatabase;
import com.example.egear.room.CartDAO;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartItemList;
    private Button buttonOrder;
    private AppDatabase db;
    private LinearLayout emptyCartSection, cartTotalSection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCart = view.findViewById(R.id.recycler_view_cart);
        if (getActivity() != null) {
            db = Room.databaseBuilder(getActivity(), AppDatabase.class, "cart").allowMainThreadQueries().build();
        }

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonOrder = view.findViewById(R.id.button_checkout);

        getProducts();

        cartAdapter = new CartAdapter(getContext(), cartItemList);
        recyclerViewCart.setAdapter(cartAdapter);

        if(cartItemList.isEmpty()){
            emptyCartSection.setVisibility(View.VISIBLE);
            cartTotalSection.setVisibility(View.GONE);
        } else {
            emptyCartSection.setVisibility(View.GONE);
            cartTotalSection.setVisibility(View.VISIBLE);
        }

        buttonOrder.setOnClickListener(v -> {
            List<Cart> selectedItems = cartAdapter.getSelectedItems();
            if (!selectedItems.isEmpty()) {
//                Intent intent = new Intent(getContext(), com.example.egear.customer.OrderActivity.class);
//                intent.putParcelableArrayListExtra("selected_items", new ArrayList<>(selectedItems));
//                intent.putExtra("selected_items", new ArrayList<>(selectedItems));
//                startActivity(intent);

            }
        });
    }

    private void getProducts() {
        emptyCartSection = getView().findViewById(R.id.no_items_in_cart_section);
        cartTotalSection = getView().findViewById(R.id.cart_total_section);
        cartItemList = new ArrayList<>();
        CartDAO cartDAO = db.getCartDAO();
        List<com.example.egear.room.Cart> carts = cartDAO.getCarts();
        System.out.println(carts);
        if (carts != null) {
            if (carts.isEmpty()) {
                recyclerViewCart.setVisibility(View.GONE);
                emptyCartSection.setVisibility(View.VISIBLE);
                cartTotalSection.setVisibility(View.GONE);
            } else {
                emptyCartSection.setVisibility(View.GONE);
                for (com.example.egear.room.Cart cart : carts) {
                    cartItemList.add(new Cart(cart.getName(), cart.getPrice(), 1, cart.getImage()));
                }
            }
        }
    }
}
