package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PlacesAdapter;
import com.example.myapplication.models.Place;
import java.util.ArrayList;
import java.util.List;

public class PlacesListActivity extends AppCompatActivity {

    private List<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        // ✅ Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ✅ Enable Back Button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Debug: Check if the category is received
        String category = getIntent().getStringExtra("category");
        if (category == null) {
            category = "Tourist Sites"; // ✅ Prevent crash if null
        }
        Log.d("PlacesListActivity", "Received category: " + category);

        setTitle(category);

        places = getPlacesByCategory(category);

        // ✅ Debug: Log the number of places loaded
        if (places == null || places.isEmpty()) {
            Log.e("PlacesListActivity", "Error: No places found for category: " + category);
            finish(); // ✅ Prevents crash by closing activity
            return;
        }

        PlacesAdapter adapter = new PlacesAdapter(this, places);
        recyclerView.setAdapter(adapter);
    }

    // ✅ Inflate menu to keep it consistent across activities
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // ✅ Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish(); // ✅ Navigate back to previous screen
            return true;
        } else if (id == R.id.action_tourist_sites) {
            openCategory("Tourist Sites");
        } else if (id == R.id.action_hotels) {
            openCategory("Hotels");
        } else if (id == R.id.action_restaurants) {
            openCategory("Restaurants");
        } else if (id == R.id.action_toggle_language) {
            toggleLanguage();
        }
        return super.onOptionsItemSelected(item);
    }

    // ✅ Open category when menu item is clicked
    private void openCategory(String category) {
        Intent intent = new Intent(this, PlacesListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    // ✅ Toggle between English and Arabic
    private void toggleLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String currentLang = prefs.getString("language", "en");
        String newLang = currentLang.equals("en") ? "ar" : "en";

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", newLang);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // ✅ Get places by category
    private List<Place> getPlacesByCategory(String category) {
        List<Place> list = new ArrayList<>();
        if (category.equals("Tourist Sites")) {
            list.add(new Place("Eiffel Tower", "Famous landmark in Paris", R.drawable.eiffel_tower, "+33 1 23 45 67 89", "https://www.toureiffel.paris"));
        } else if (category.equals("Hotels")) {
            list.add(new Place("Hilton Hotel", "Luxury hotel with great services", R.drawable.hotel, "+1 800 445 8667", "https://www.hilton.com"));
        } else if (category.equals("Restaurants")) {
            list.add(new Place("Le Gourmet", "Fine dining with French cuisine", R.drawable.restaurant, "+33 1 98 76 54 32", "https://www.legourmet.fr"));
        }
        return list;
    }
}
