package com.example.egear;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button buttonFragHome, buttonFragProfile, buttonFragSettings;

    private Button currentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonFragHome = (Button) findViewById(R.id.btnFragHome);
        buttonFragProfile = (Button) findViewById(R.id.btnFragProfile);
        buttonFragSettings = (Button) findViewById(R.id.btnFragSetting);

        buttonFragHome.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new HomeFragment()).commit();
            if (currentButton != null) {
                currentButton.setBackgroundColor(getResources().getColor(R.color.white)); // replace 'defaultButtonColor' with your default button color
            }
            view.setBackgroundColor(getResources().getColor(R.color.cyan));
            currentButton = (Button) view;
        });

        buttonFragProfile.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new ProfileFragment()).commit();
            if (currentButton != null) {
                currentButton.setBackgroundColor(getResources().getColor(R.color.white)); // replace 'defaultButtonColor' with your default button color
            }
            view.setBackgroundColor(getResources().getColor(R.color.cyan));
            currentButton = (Button) view;
        });

        buttonFragSettings.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, new SettingFragment()).commit();
            if (currentButton != null) {
                currentButton.setBackgroundColor(getResources().getColor(R.color.white)); // replace 'defaultButtonColor' with your default button color
            }
            view.setBackgroundColor(getResources().getColor(R.color.cyan));
            currentButton = (Button) view;
        });
    }
}