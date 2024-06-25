package com.example.egear.tabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.egear.R;
import com.example.egear.customer.products.Product;
import com.example.egear.customer.products.ProductAdapter;
import com.example.egear.customer.products.ProductDetail;
import com.example.egear.customer.products.ProductResponse;
import com.example.egear.customer.products.ProductService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    Button btn1, btn2, btn3, btn4, btn5;
    ImageView filterButton;

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
        System.out.println(products);
        adapter = new ProductAdapter(products);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);

        btn1 = view.findViewById(R.id.button1);
        btn2 = view.findViewById(R.id.button2);
        btn3 = view.findViewById(R.id.button3);
        btn4 = view.findViewById(R.id.button4);
        btn5 = view.findViewById(R.id.button5);
        filterButton = view.findViewById(R.id.filterBtn);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product, int position) {
                Intent intent = new Intent(getActivity(), ProductDetail.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllButtons();
                lookSelectedButton(btn1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllButtons();
                lookSelectedButton(btn2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllButtons();
                lookSelectedButton(btn3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllButtons();
                lookSelectedButton(btn4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSelectAllButtons();
                lookSelectedButton(btn5);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_filter, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
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
//                System.out.println(response.body().getData());
                products.addAll(response.body().getData());
                adapter = new ProductAdapter(products);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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

    private void lookSelectedButton(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.cyan));
    }

    private void lookUnSelectedButton(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void unSelectAllButtons() {
        lookUnSelectedButton(btn1);
        lookUnSelectedButton(btn2);
        lookUnSelectedButton(btn3);
        lookUnSelectedButton(btn4);
        lookUnSelectedButton(btn5);
    }
}