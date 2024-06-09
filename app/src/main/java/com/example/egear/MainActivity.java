package com.example.egear;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egear.databinding.ActivityMainBinding;
import com.example.egear.tabs.CartFragment;
import com.example.egear.tabs.ComboFragment;
import com.example.egear.tabs.HomeFragment;
import com.example.egear.tabs.ProfileFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //    RecyclerView recyclerView;
    //    ArrayList<Post> posts;
    //    PostAdapter adapter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                fragment = new HomeFragment();
            } else if (itemId == R.id.combo) {
                fragment = new ComboFragment();
            } else if (itemId == R.id.cart) {
                fragment = new CartFragment();
            } else if (itemId == R.id.profile) {
                fragment = new ProfileFragment();
            }
            replaceFragment(fragment);
            return true;
        });

//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostService jsonPlaceholder = retrofit.create(PostService.class);
//        Call<ArrayList<Post>> call = jsonPlaceholder.getPosts();
//        call.enqueue(new Callback<ArrayList<Post>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
//                if(!response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                posts = new ArrayList<>(response.body());
//                adapter = new PostAdapter(posts);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, fragment);
        transaction.commit();
    }
}