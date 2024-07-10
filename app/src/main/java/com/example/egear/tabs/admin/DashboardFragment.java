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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egear.R;
import com.example.egear.admin.dashboard.DashboardService;
import com.example.egear.admin.dashboard.OrderAdapter;
import com.example.egear.admin.dashboard.OrderHistory;
import com.example.egear.admin.dashboard.OrderResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    Spinner spinner;

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
        spinner = view.findViewById(R.id.filterMonth);
        productSoldCount = view.findViewById(R.id.productSoldCount);
        totalIncome = view.findViewById(R.id.totalIncome);
        topCustomer = view.findViewById(R.id.topCustomer);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getDashboardData();
        setAdapter(orderHistories);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderHistories = new ArrayList<>();
                String month = spinner.getSelectedItem().toString();
                getFilteredDashboardData(month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getDashboardData();
            }
        });
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
                    progressBar.setVisibility(View.GONE);
                    OrderResponse orderResponse = response.body();
                    productSoldCount.setText(String.valueOf(orderResponse.getData().getProduct_sold_count()));
                    totalIncome.setText("$" + orderResponse.getData().getTotal_income());
                    topCustomer.setText(orderResponse.getData().getTop_customer().getUsername());
                    orderHistories.addAll(orderResponse.getData().getCustomer_orders());
                    setAdapter(orderHistories);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Failed to get dashboard data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getFilteredDashboardData(String month) {
        progressBar.setVisibility(View.VISIBLE);
        orderHistories = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DashboardService dashboardService = retrofit.create(DashboardService.class);
        Call<OrderResponse> call = dashboardService.getFilteredDashboardData("Bearer " + token, month);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    OrderResponse orderResponse = response.body();
                    productSoldCount.setText(String.valueOf(orderResponse.getData().getProduct_sold_count()));
                    totalIncome.setText("$" + orderResponse.getData().getTotal_income());
                    topCustomer.setText(orderResponse.getData().getTop_customer().getUsername());
                    orderHistories.addAll(orderResponse.getData().getCustomer_orders());
                    setAdapter(orderHistories);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Month " + month + " has no data", Toast.LENGTH_SHORT).show();
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
        if (!orderHistories.isEmpty()) {
            adapter = new OrderAdapter(getActivity(), orderHistories);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }
}