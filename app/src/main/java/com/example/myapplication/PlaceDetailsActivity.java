package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.myapplication.adapters.PhotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class PlaceDetailsActivity extends BaseActivity implements PhotoAdapter.OnPhotoClickListener {

    private static final String TAG = "PlaceDetailsActivity";
    private TextView placeName, placeDescription, reviewCount;
    private RatingBar ratingBar;
    private ImageView iconCall, iconSms, iconEmail, iconWebsite, iconMap;
    private RecyclerView photoGallery;
    private LinearLayout dotsContainer;
    private String phoneNumber, websiteUrl, email;
    private List<Integer> imageResIds;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

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

        placeName = findViewById(R.id.textViewPlaceName);
        placeDescription = findViewById(R.id.textViewPlaceDescription);
        ratingBar = findViewById(R.id.ratingBar);
        reviewCount = findViewById(R.id.reviewCount);
        iconCall = findViewById(R.id.iconCall);
        iconSms = findViewById(R.id.iconSms);
        iconEmail = findViewById(R.id.iconEmail);
        iconWebsite = findViewById(R.id.iconWebsite);
        iconMap = findViewById(R.id.iconMap);
        photoGallery = findViewById(R.id.photoGallery);
        dotsContainer = findViewById(R.id.dotsContainer);

        Intent intent = getIntent();
        String name = intent.getStringExtra("place_name");
        String description = intent.getStringExtra("place_description");
        imageResIds = intent.getIntegerArrayListExtra("place_images");
        if (imageResIds == null || imageResIds.isEmpty()) {
            imageResIds = Arrays.asList(R.drawable.ic_launcher_foreground);
        }
        phoneNumber = intent.getStringExtra("place_phone");
        websiteUrl = intent.getStringExtra("place_website");
        email = intent.getStringExtra("place_email");
        float rating = intent.getFloatExtra("place_rating", 0.0f);
        int reviewCountValue = intent.getIntExtra("place_review_count", 0);

        if (name == null || description == null) {
            Log.e(TAG, "Error: Missing place details");
            Toast.makeText(this, "Error: Place details unavailable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        placeName.setText(name);
        placeDescription.setText(description);
        ratingBar.setRating(rating);
        reviewCount.setText(getString(R.string.review_count, reviewCountValue));
        setTitle(name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        if (imageResIds.size() == 1) {
            layoutManager.setInitialPrefetchItemCount(1);
            photoGallery.setPadding(0, 8, 0, 8);
        }
        photoGallery.setLayoutManager(layoutManager);
        PhotoAdapter photoAdapter = new PhotoAdapter(this, imageResIds, this, false, false);
        photoGallery.setAdapter(photoAdapter);
        setupDots(imageResIds.size());

        iconCall.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
                Toast.makeText(this, "Dialing " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        iconSms.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
                smsIntent.putExtra("sms_body", "Hello, I’d like more info about " + name);
                startActivity(smsIntent);
                Toast.makeText(this, "Opening SMS for " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        iconEmail.setOnClickListener(v -> {
            if (email != null && !email.isEmpty()) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about " + name);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I’m interested in learning more about " + name + ".");
                startActivity(emailIntent);
                Toast.makeText(this, "Sending email about " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No email available", Toast.LENGTH_SHORT).show();
            }
        });

        iconWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl)));
                Toast.makeText(this, "Opening website for " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No website available", Toast.LENGTH_SHORT).show();
            }
        });

        iconMap.setOnClickListener(v -> {
            String geoUri = "geo:0,0?q=" + Uri.encode(name + ", Algiers");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)));
            Toast.makeText(this, "Showing " + name + " on map", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
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

    private void setupDots(int count) {
        dotsContainer.removeAllViews();
        if (count <= 1) {
            dotsContainer.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    i == 0 ? 16 : 12, // 16dp for active, 12dp for inactive
                    i == 0 ? 16 : 12
            );
            params.setMargins(8, 0, 8, 0); // Increased spacing
            dot.setLayoutParams(params);
            dot.setImageResource(i == 0 ? R.drawable.dot_active : R.drawable.dot_inactive);
            dotsContainer.addView(dot);
        }
        photoGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                updateDots(firstVisible);
            }
        });
    }

    private void updateDots(int activePosition) {
        for (int i = 0; i < dotsContainer.getChildCount(); i++) {
            ImageView dot = (ImageView) dotsContainer.getChildAt(i);
            dot.setImageResource(i == activePosition ? R.drawable.dot_active : R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    i == activePosition ? 16 : 12,
                    i == activePosition ? 16 : 12
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
        }
    }

    @Override
    public void onPhotoClick(int position) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_photo_viewer);

        ViewPager2 viewPager = dialog.findViewById(R.id.viewPager);
        ImageView closeButton = dialog.findViewById(R.id.closeButton);
        PhotoAdapter fullPhotoAdapter = new PhotoAdapter(this, imageResIds, pos -> {}, true, false);
        viewPager.setAdapter(fullPhotoAdapter);
        viewPager.setCurrentItem(position, false);

        closeButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
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
}