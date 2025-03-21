package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void attachBaseContext(Context base) {
        SharedPreferences prefs = base.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("language", "en");
        Log.d(TAG, "Attaching base context with locale: " + language);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        Context newContext = base.createConfigurationContext(config);

        super.attachBaseContext(newContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_tourist_sites) {
            openCategory("Tourist Sites");
        } else if (id == R.id.action_hotels) {
            openCategory("Hotels");
        } else if (id == R.id.action_restaurants) {
            openCategory("Restaurants");
        } else if (id == R.id.action_toggle_language) {
            toggleLanguage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void toggleLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String currentLang = prefs.getString("language", "en");
        String newLang = currentLang.equals("en") ? "ar" : "en";
        Log.d(TAG, "Toggling language to: " + newLang);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", newLang);
        editor.commit(); // Use commit() instead of apply() to ensure synchronous save

        // Restart the app
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Ensure current activity is finished
    }

    protected void openCategory(String category) {
        Intent intent = new Intent(this, PlacesListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}