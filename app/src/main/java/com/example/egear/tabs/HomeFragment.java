package com.example.egear.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.egear.R;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.product_recycler_view);
        getProducts();
        adapter = new ProductAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void getProducts() {
        products = new ArrayList<>();
        products.add(new Product("1", "Product 1", "100", "Category 1", 10));
        products.add(new Product("2", "Product 2", "200", "Category 2", 20));
        products.add(new Product("3", "Product 3", "300", "Category 3", 30));
        products.add(new Product("4", "Product 4", "400", "Category 4", 40));
    }
}