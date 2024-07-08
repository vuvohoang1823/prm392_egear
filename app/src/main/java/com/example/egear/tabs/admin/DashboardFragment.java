package com.example.egear.tabs.admin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egear.R;
import com.example.egear.admin.dashboard.DashboardService;
import com.example.egear.admin.dashboard.OrderAdapter;
import com.example.egear.admin.dashboard.OrderHistory;
import com.example.egear.admin.dashboard.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {
    TextView productSoldCount, totalIncome, topCustomer;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    OrderAdapter adapter;
    List<OrderHistory> orderHistories;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.orderHistoryRecycler);
        progressBar = view.findViewById(R.id.progressBar);
        productSoldCount = view.findViewById(R.id.productSoldCount);
        totalIncome = view.findViewById(R.id.totalIncome);
        topCustomer = view.findViewById(R.id.topCustomer);

        getDashboardData();
        setAdapter(orderHistories);
    }

    private void getDashboardData() {
        progressBar.setVisibility(View.VISIBLE);
        orderHistories = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DashboardService dashboardService = retrofit.create(DashboardService.class);
        Call<OrderResponse> call = dashboardService.getDashboardData("Bearer " + token);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    OrderResponse orderResponse = response.body();
                    productSoldCount.setText(String.valueOf(orderResponse.getData().getProduct_sold_count()));
                    totalIncome.setText("$" + orderResponse.getData().getTotal_income());
                    topCustomer.setText(orderResponse.getData().getTop_customer().getUsername());
                    orderHistories.addAll(orderResponse.getData().getCustomer_orders());
                    setAdapter(orderHistories);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to get dashboard data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<OrderHistory> orderHistories) {
        if(!orderHistories.isEmpty()) {
            adapter = new OrderAdapter(getActivity(), orderHistories);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }
}