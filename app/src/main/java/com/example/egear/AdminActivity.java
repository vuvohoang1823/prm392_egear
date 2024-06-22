package com.example.egear;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.egear.databinding.ActivityAdminBinding;
import com.example.egear.tabs.admin.ProductFragment;
import com.example.egear.tabs.admin.ComboFragment;
import com.example.egear.tabs.ProfileFragment;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new ProductFragment());
        binding.bottomNavigationViewAdmin.setBackground(null);
        binding.bottomNavigationViewAdmin.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.admin_home) {
                fragment = new ProductFragment();
            } else if (itemId == R.id.admin_combo) {
                fragment = new ComboFragment();
            } else if (itemId == R.id.admin_profile) {
                fragment = new ProfileFragment();
            }
            replaceFragment(fragment);
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragmentAdmin, fragment);
        transaction.commit();
    }
}
