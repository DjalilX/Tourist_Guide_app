package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PlacesAdapter;
import com.example.myapplication.models.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlacesListActivity extends BaseActivity {

    private static final String TAG = "PlacesListActivity";
    private PlacesAdapter adapter;
    private String category;
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

        // Initialize category
        category = getIntent().getStringExtra("category");
        if (category == null) category = "Tourist Sites";

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(getMenuItemIdForCategory(category));
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Log.d(TAG, "Selected item: " + itemId + ", Current category: " + category);
            if (itemId == R.id.nav_home) {
                Log.d(TAG, "Navigating to MainActivity");
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                return true;
            }
            String newCategory = null;
            if (itemId == R.id.nav_hotels) newCategory = "Hotels";
            else if (itemId == R.id.nav_restaurants) newCategory = "Restaurants";
            else if (itemId == R.id.nav_tourist_sites) newCategory = "Tourist Sites";
            else if (itemId == R.id.nav_favorites) newCategory = "Favorites";
            if (newCategory != null && !newCategory.equals(category)) {
                Log.d(TAG, "Navigating to PlacesListActivity with category: " + newCategory);
                Intent intent = new Intent(this, PlacesListActivity.class);
                intent.putExtra("category", newCategory);
                startActivity(intent);
                finish();
                return true;
            }
            Log.d(TAG, "Same or invalid category selected");
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.layout_fade_in));

        Log.d(TAG, "Received category: " + category);

        setTitle(getString(category.equals("Tourist Sites") ? R.string.title_tourist_sites :
                category.equals("Hotels") ? R.string.title_hotels :
                        category.equals("Restaurants") ? R.string.title_restaurants :
                                R.string.category_favorites));

        progressBar.setVisibility(View.VISIBLE);
        places = getPlacesByCategory(category);

        if (places == null || places.isEmpty()) {
            Log.e(TAG, "Error: No places found for category: " + category);
            progressBar.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            adapter = new PlacesAdapter(this, places);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }

    public void onFavoriteClick(View view) {
        int position = ((RecyclerView) findViewById(R.id.recyclerView))
                .getChildAdapterPosition((View) view.getParent().getParent());
        if (position != RecyclerView.NO_POSITION) {
            Place place = places.get(position);
            boolean isFavorite = !place.isFavorite();
            place.setFavorite(isFavorite);

            SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
            Set<String> favorites = new HashSet<>(prefs.getStringSet("favorite_places", new HashSet<>()));
            if (isFavorite) favorites.add(place.getId());
            else favorites.remove(place.getId());
            prefs.edit().putStringSet("favorite_places", favorites).apply();

            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.action_language) {
            super.toggleLanguage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getMenuItemIdForCategory(String category) {
        switch (category) {
            case "Hotels":
                return R.id.nav_hotels;
            case "Restaurants":
                return R.id.nav_restaurants;
            case "Tourist Sites":
                return R.id.nav_tourist_sites;
            case "Favorites":
                return R.id.nav_favorites;
            default:
                return R.id.nav_tourist_sites;
        }
    }

    private List<Place> getPlacesByCategory(String category) {
        List<Place> list = new ArrayList<>();
        try {
            InputStream is = getAssets().open("places.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            SharedPreferences prefs = getSharedPreferences("Favorites", MODE_PRIVATE);
            Set<String> favorites = prefs.getStringSet("favorite_places", new HashSet<>());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                boolean isFavoriteCategory = category.equals("Favorites");
                boolean matchesCategory = obj.getString("category").equals(category);
                boolean isFavorite = favorites.contains(obj.getString("id"));

                if ((isFavoriteCategory && isFavorite) || (!isFavoriteCategory && matchesCategory)) {
                    String nameKey = obj.getString("name");
                    String descKey = obj.getString("desc");
                    int nameResId = getResources().getIdentifier(nameKey, "string", getPackageName());
                    int descResId = getResources().getIdentifier(descKey, "string", getPackageName());
                    String name = nameResId != 0 ? getString(nameResId) : nameKey;
                    String desc = descResId != 0 ? getString(descResId) : descKey;

                    List<Integer> imageResIds = new ArrayList<>();
                    if (obj.has("images")) {
                        JSONArray imagesArray = obj.getJSONArray("images");
                        for (int j = 0; j < imagesArray.length(); j++) {
                            int imageResId = getResources().getIdentifier(imagesArray.getString(j), "drawable", getPackageName());
                            if (imageResId != 0) imageResIds.add(imageResId);
                        }
                    } else if (obj.has("image")) {
                        int imageResId = getResources().getIdentifier(obj.getString("image"), "drawable", getPackageName());
                        if (imageResId != 0) imageResIds.add(imageResId);
                    }
                    if (imageResIds.isEmpty()) {
                        imageResIds.add(R.drawable.ic_launcher_foreground);
                    }

                    list.add(new Place(
                            obj.getString("id"),
                            name,
                            desc,
                            imageResIds,
                            obj.getString("phone"),
                            obj.getString("website"),
                            obj.getString("email"),
                            (float) obj.getDouble("rating"),
                            obj.getInt("reviewCount"),
                            isFavorite,
                            obj.getString("category")
                    ));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON: " + e.getMessage());
        }
        return list;
    }
}