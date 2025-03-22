package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PlacesAdapter;
import com.example.myapplication.models.Place;
import java.util.ArrayList;
import java.util.List;

public class PlacesListActivity extends BaseActivity {

    private static final String TAG = "PlacesListActivity";

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
        ProgressBar progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String category = getIntent().getStringExtra("category");
        if (category == null) {
            category = "Tourist Sites";
        }
        Log.d(TAG, "Received category: " + category);

        // Set title early for responsiveness
        setTitle(getString(category.equals("Tourist Sites") ? R.string.title_tourist_sites :
                category.equals("Hotels") ? R.string.title_hotels : R.string.title_restaurants));

        progressBar.setVisibility(View.VISIBLE); // Show loading
        List<Place> places = getPlacesByCategory(category);

        if (places == null || places.isEmpty()) {
            Log.e(TAG, "Error: No places found for category: " + category);
            Toast.makeText(this, "No places available for " + category, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            finish();
            return;
        }

        PlacesAdapter adapter = new PlacesAdapter(this, places);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE); // Hide loading

        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }

    private List<Place> getPlacesByCategory(String category) {
        List<Place> list = new ArrayList<>();
        if (category.equals("Tourist Sites")) {
            setTitle(getString(R.string.title_tourist_sites));
            list.add(new Place(getString(R.string.martyrs_memorial_name),
                    getString(R.string.martyrs_memorial_desc),
                    R.drawable.martyrs_memorial, "+213 21 23 45 67", "https://www.algeriatourism.dz", "info@martyrs.dz"));
            list.add(new Place(getString(R.string.notre_dame_name),
                    getString(R.string.notre_dame_desc),
                    R.drawable.notre_dame, "+213 21 78 90 12", "https://www.notredamealger.dz", "contact@notredame.dz"));
            list.add(new Place(getString(R.string.casbah_name),
                    getString(R.string.casbah_desc),
                    R.drawable.casbah, "+213 21 34 56 78", "https://www.casbahalger.dz", "casbah@tourism.dz"));
        } else if (category.equals("Hotels")) {
            setTitle(getString(R.string.title_hotels));
            list.add(new Place(getString(R.string.el_aurassi_name),
                    getString(R.string.el_aurassi_desc),
                    R.drawable.el_aurassi, "+213 21 74 82 52", "https://www.el-aurassi.com", "reservation@el-aurassi.com"));
            list.add(new Place(getString(R.string.sofitel_name),
                    getString(R.string.sofitel_desc),
                    R.drawable.sofitel, "+213 21 68 52 10", "https://www.sofitel-alger.com", "book@sofitel-alger.com"));
            list.add(new Place(getString(R.string.marriott_name),
                    getString(R.string.marriott_desc),
                    R.drawable.marriott, "+213 23 47 80 00", "https://www.marriott.com/alger", "info@marriott-alger.com"));
        } else if (category.equals("Restaurants")) {
            setTitle(getString(R.string.title_restaurants));
            list.add(new Place(getString(R.string.le_tantra_name),
                    getString(R.string.le_tantra_desc),
                    R.drawable.le_tantra, "+213 21 60 12 34", "https://www.letantra.dz", "tantra@restau.dz"));
            list.add(new Place(getString(R.string.dar_el_qods_name),
                    getString(R.string.dar_el_qods_desc),
                    R.drawable.dar_el_qods, "+213 21 56 78 90", "https://www.darelqods.dz", "contact@darelqods.dz"));
            list.add(new Place(getString(R.string.el_djazair_name),
                    getString(R.string.el_djazair_desc),
                    R.drawable.el_djazair, "+213 21 23 45 67", "https://www.eldjazair.dz", "info@eldjazair.dz"));
        }
        return list;
    }
}