package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvStudents = findViewById(R.id.tvStudents);
        Button btnChangeLanguage = findViewById(R.id.btnChangeLanguage);
        Button btnTouristSites = findViewById(R.id.btnTouristSites);
        Button btnHotels = findViewById(R.id.btnHotels);
        Button btnRestaurants = findViewById(R.id.btnRestaurants);
        Button btnFavorites = findViewById(R.id.btnFavorites);

        tvDescription.setText(R.string.app_description);
        tvStudents.setText(R.string.student_names);

        btnChangeLanguage.setOnClickListener(v -> {
            toggleLanguage();
            recreate();
        });

        btnTouristSites.setOnClickListener(v -> startPlacesActivity("Tourist Sites"));
        btnHotels.setOnClickListener(v -> startPlacesActivity("Hotels"));
        btnRestaurants.setOnClickListener(v -> startPlacesActivity("Restaurants"));
        btnFavorites.setOnClickListener(v -> startPlacesActivity("Favorites"));

        Log.d(TAG, "MainActivity created with locale: " + getResources().getConfiguration().getLocales().get(0));
    }

    private void startPlacesActivity(String category) {
        Intent intent = new Intent(this, PlacesListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet("favorite_places", new HashSet<>());
        Button btnFavorites = findViewById(R.id.btnFavorites);
        btnFavorites.setEnabled(!favorites.isEmpty());
    }
}