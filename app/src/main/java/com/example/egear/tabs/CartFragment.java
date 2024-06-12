package com.example.egear.tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.egear.R;
import com.example.egear.customer.cart.Cart;
import com.example.egear.customer.cart.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCart = view.findViewById(R.id.recycler_view_cart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data for testing
        cartItemList = new ArrayList<>();
        cartItemList.add(new Cart("Product 1", 10.99, 1, R.drawable.ic_launcher_background));
        cartItemList.add(new Cart("Product 2", 12.99, 2, R.drawable.ic_launcher_background));
        cartItemList.add(new Cart("Product 3", 8.99, 3, R.drawable.ic_launcher_background));

        cartAdapter = new CartAdapter(getContext(), cartItemList);
        recyclerViewCart.setAdapter(cartAdapter);
    }
}
