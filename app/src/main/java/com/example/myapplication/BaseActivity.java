package com.example.myapplication;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale();
    }

    protected void toggleLanguage() {
        String currentLang = getResources().getConfiguration().getLocales().get(0).getLanguage();
        String newLang = currentLang.equals("en") ? "ar" : "en";
        Locale locale = new Locale(newLang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getSharedPreferences("Settings", MODE_PRIVATE)
                .edit()
                .putString("lang", newLang)
                .apply();
    }

    private void setLocale() {
        String currentLang = getSharedPreferences("Settings", MODE_PRIVATE)
                .getString("lang", Locale.getDefault().getLanguage());
        Locale locale = new Locale(currentLang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}