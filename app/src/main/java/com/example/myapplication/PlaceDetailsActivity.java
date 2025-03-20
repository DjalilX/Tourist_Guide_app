package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

public class PlaceDetailsActivity extends AppCompatActivity {

    private ImageView placeImage;
    private TextView placeName, placeDescription;
    private Button btnCall, btnWebsite;
    private String phoneNumber, websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        // ✅ Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ✅ Enable Back Button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // ✅ Initialize UI elements
        placeImage = findViewById(R.id.imageViewPlace);
        placeName = findViewById(R.id.textViewPlaceName);
        placeDescription = findViewById(R.id.textViewPlaceDescription);
        btnCall = findViewById(R.id.btnCall);
        btnWebsite = findViewById(R.id.btnWebsite);

        // ✅ Get intent data safely
        String name = getIntent().getStringExtra("place_name");
        String description = getIntent().getStringExtra("place_description");
        int imageRes = getIntent().getIntExtra("place_image", R.drawable.ic_launcher_foreground);
        phoneNumber = getIntent().getStringExtra("place_phone");
        websiteUrl = getIntent().getStringExtra("place_website");

        // ✅ Validate required data
        if (name == null || description == null) {
            Log.e("PlaceDetailsActivity", "Error: Missing place details");
            finish();
            return;
        }

        // ✅ Set data to UI elements
        placeName.setText(name);
        placeDescription.setText(description);
        placeImage.setImageResource(imageRes);

        // ✅ Handle Call Button
        btnCall.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                Log.e("PlaceDetailsActivity", "Error: Phone number is missing!");
            }
        });

        // ✅ Handle Website Button
        btnWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                startActivity(webIntent);
            } else {
                Log.e("PlaceDetailsActivity", "Error: Website URL is missing!");
            }
        });
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
            finish(); // ✅ Navigate back to the previous screen
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
}
