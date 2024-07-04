package com.example.egear.tabs.admin;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.egear.R;
import com.example.egear.admin.combo.AddEditCombo;
import com.example.egear.admin.combo.ComboAdapter;
import com.example.egear.admin.combo.ComboDetail;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboResponse;
import com.example.egear.customer.combo.ComboService;
import com.example.egear.customer.products.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    ImageView btnAdd ,btnFilter;
    TextView noCombo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin_fragment_combo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.admin_combo_recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        btnAdd = view.findViewById(R.id.buttonAddCombo);
        btnFilter = view.findViewById(R.id.buttonFilterCombo);
        noCombo = view.findViewById(R.id.no_combo_found);
        getCombos();
        setAdapter(combos);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditCombo.class);
                startActivity(intent);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_filter, null);
                Button btnAll = view.findViewById(R.id.buttonAll);
                Button btnActive = view.findViewById(R.id.buttonActive);
                Button btnDeleted = view.findViewById(R.id.buttonDeleted);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                btnAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        setAdapter(combos);
                    }
                });

                btnActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        List<Combo> activeCombos = new ArrayList<>();
                        for(Combo combo : combos) {
                            if(combo.getStatus().equals("ACTIVE")) {
                                activeCombos.add(combo);
                            }
                        }
                        setAdapter(activeCombos);
                    }
                });

                btnDeleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        List<Combo> deletedCombos = new ArrayList<>();
                        for(Combo combo : combos) {
                            if(combo.getStatus().equals("DELETED")) {
                                deletedCombos.add(combo);
                            }
                        }
                        setAdapter(deletedCombos);
                    }
                });
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
        Call<ComboResponse> call = comboService.getCombos("Bearer " + token);

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
                setAdapter(combos);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ComboResponse> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setAdapter(List<Combo> combos) {
        if(!combos.isEmpty()) {
            noCombo.setVisibility(View.GONE);
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
        } else {
            noCombo.setVisibility(View.VISIBLE);
            adapter = new ComboAdapter(combos);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            recyclerView.setAdapter(adapter);
        }
    }
}