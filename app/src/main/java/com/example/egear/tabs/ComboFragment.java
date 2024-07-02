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
import android.widget.ProgressBar;

import com.example.egear.R;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboAdapter;
import com.example.egear.customer.combo.ComboDetail;
import com.example.egear.customer.combo.ComboResponse;
import com.example.egear.customer.combo.ComboService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComboFragment extends Fragment {
    RecyclerView recyclerView;
    List<Combo> combos;
    ComboAdapter adapter;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_combo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.combo_recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        getCombos();
        adapter = new ComboAdapter(combos);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ComboAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Combo combo, int position) {
                Intent intent = new Intent(getActivity(), ComboDetail.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
            }
        });
    }

    private void getCombos() {
        progressBar.setVisibility(View.VISIBLE);
        combos = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ComboService comboService = retrofit.create(ComboService.class);
        Call<ComboResponse> call = comboService.getActiveCombos("Bearer " + token);

        call.enqueue(new Callback<ComboResponse>() {
            @Override
            public void onResponse(Call<ComboResponse> call, Response<ComboResponse> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    progressBar.setVisibility(View.GONE);
                    return;
                }
//                System.out.println(response.body().getData());
                combos.addAll(response.body().getData());
                adapter = new ComboAdapter(combos);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new ComboAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Combo combo, int position) {
                        Intent intent = new Intent(getActivity(), ComboDetail.class);
                        intent.putExtra("combo", combo);
                        startActivity(intent);
                    }
                });
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ComboResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}