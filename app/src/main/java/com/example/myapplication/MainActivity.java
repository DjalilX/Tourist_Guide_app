package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PhotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView descriptionCarousel;
    private Handler handler;
    private Runnable autoScrollRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);

        // Set header image
        ShapeableImageView headerImage = findViewById(R.id.headerImage);
        headerImage.setImageResource(R.drawable.algiers_header);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            }
            String category = null;
            if (itemId == R.id.nav_hotels) {
                category = "Hotels";
            } else if (itemId == R.id.nav_restaurants) {
                category = "Restaurants";
            } else if (itemId == R.id.nav_tourist_sites) {
                category = "Tourist Sites";
            } else if (itemId == R.id.nav_favorites) {
                category = "Favorites";
            }
            if (category != null) {
                Intent intent = new Intent(this, PlacesListActivity.class);
                intent.putExtra("category", category);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Setup Description Carousel
        descriptionCarousel = findViewById(R.id.descriptionCarousel);
        descriptionCarousel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Integer> carouselImages = Arrays.asList(
                R.drawable.martyrs_memorial,
                R.drawable.casbah,
                R.drawable.martyrs_memorial2,
                R.drawable.sofitel,
                R.drawable.martyrs_memorial3,
                R.drawable.el_djazair,
                R.drawable.martyrs_memorial4,
                R.drawable.el_aurassi
        );

        PhotoAdapter carouselAdapter = new PhotoAdapter(this, carouselImages, position -> {}, false, true);
        descriptionCarousel.setAdapter(carouselAdapter);

        // Auto-scroll carousel
        handler = new Handler(Looper.getMainLooper());
        autoScrollRunnable = new Runnable() {
            int currentPosition = 0;
            @Override
            public void run() {
                currentPosition = (currentPosition + 1) % carouselImages.size();
                descriptionCarousel.smoothScrollToPosition(currentPosition);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(autoScrollRunnable, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_language) {
            super.toggleLanguage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && autoScrollRunnable != null) {
            handler.removeCallbacks(autoScrollRunnable);
        }
    }
}