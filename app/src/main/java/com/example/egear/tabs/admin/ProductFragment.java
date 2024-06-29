package com.example.egear.tabs.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.egear.R;
import com.example.egear.customer.products.Product;
import com.example.egear.admin.products.ProductAdapter;
import com.example.egear.admin.products.ProductDetail;
import com.example.egear.customer.products.ProductResponse;
import com.example.egear.customer.products.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin_fragment_product, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.product_recycler_view_admin);
        getProducts();
        adapter = new ProductAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        btnAdd = view.findViewById(R.id.buttonAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm sản phẩm
            }
        });

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product, int position) {
                Intent intent = new Intent(getActivity(), ProductDetail.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
    }

    private void getProducts() {
        products = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductService jsonPlaceholder = retrofit.create(ProductService.class);
        Call<ProductResponse> call = jsonPlaceholder.getProducts("Bearer " + token);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                products.addAll(response.body().getData());
                adapter = new ProductAdapter(products);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Product product, int position) {
                        Intent intent = new Intent(getActivity(), ProductDetail.class);
                        intent.putExtra("product", product);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}