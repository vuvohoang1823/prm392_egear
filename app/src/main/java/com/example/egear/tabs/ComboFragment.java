package com.example.egear.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.egear.R;
import com.example.egear.customer.combo.Combo;
import com.example.egear.customer.combo.ComboAdapter;
import com.example.egear.customer.combo.ComboDetail;

import java.util.ArrayList;
import java.util.List;

public class ComboFragment extends Fragment {
    RecyclerView recyclerView;
    List<Combo> combos;
    ComboAdapter adapter;
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
        combos = new ArrayList<>();
        combos.add(new Combo("Combo 1", "Combo 1 Description", "100", 10));
        combos.add(new Combo("Combo 2", "Combo 2 Description", "200", 20));
        combos.add(new Combo("Combo 3", "Combo 3 Description", "300", 30));
        combos.add(new Combo("Combo 4", "Combo 4 Description", "400", 40));
        combos.add(new Combo("Combo 5", "Combo 5 Description", "500", 50));
        combos.add(new Combo("Combo 6", "Combo 6 Description", "600", 60));
        combos.add(new Combo("Combo 7", "Combo 7 Description", "700", 70));
        combos.add(new Combo("Combo 8", "Combo 8 Description", "800", 80));
        combos.add(new Combo("Combo 9", "Combo 9 Description", "900", 90));
        combos.add(new Combo("Combo 10", "Combo 10 Description", "1000", 100));
    }
}