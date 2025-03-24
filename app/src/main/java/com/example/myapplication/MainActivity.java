package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapters.PhotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            String category = null;
            int itemId = item.getItemId();
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
                Intent intent = new Intent(MainActivity.this, PlacesListActivity.class);
                intent.putExtra("category", category);
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
                R.drawable.sofitel,
                R.drawable.el_djazair,
                R.drawable.el_aurassi
        );
        PhotoAdapter carouselAdapter = new PhotoAdapter(this, carouselImages, position -> {}, false);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_language) {
            super.toggleLanguage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable);
    }
}