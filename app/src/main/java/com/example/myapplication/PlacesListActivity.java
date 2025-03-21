package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PlacesAdapter;
import com.example.myapplication.models.Place;
import java.util.ArrayList;
import java.util.List;

public class PlacesListActivity extends BaseActivity {

    private static final String TAG = "PlacesListActivity";
    private List<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String category = getIntent().getStringExtra("category");
        if (category == null) {
            category = "Tourist Sites";
        }
        Log.d(TAG, "Received category: " + category);

        setTitle(category);

        places = getPlacesByCategory(category);

        if (places == null || places.isEmpty()) {
            Log.e(TAG, "Error: No places found for category: " + category);
            finish();
            return;
        }

        PlacesAdapter adapter = new PlacesAdapter(this, places);
        recyclerView.setAdapter(adapter);

        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }

    // Replace the old method with this updated version
    private List<Place> getPlacesByCategory(String category) {
        List<Place> list = new ArrayList<>();
        if (category.equals("Tourist Sites")) {
            list.add(new Place(getString(R.string.eiffel_tower_name),
                    getString(R.string.eiffel_tower_desc),
                    R.drawable.eiffel_tower, "+33 1 23 45 67 89", "https://www.toureiffel.paris"));
        } else if (category.equals("Hotels")) {
            list.add(new Place(getString(R.string.hilton_hotel_name),
                    getString(R.string.hilton_hotel_desc),
                    R.drawable.hotel, "+1 800 445 8667", "https://www.hilton.com"));
        } else if (category.equals("Restaurants")) {
            list.add(new Place(getString(R.string.le_gourmet_name),
                    getString(R.string.le_gourmet_desc),
                    R.drawable.restaurant, "+33 1 98 76 54 32", "https://www.legourmet.fr"));
        }
        return list;
    }
}