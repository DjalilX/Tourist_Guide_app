package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "App Name: " + getString(R.string.app_name));
        Log.d(TAG, "Description: " + getString(R.string.app_description));
        Log.d(TAG, "Students: " + getString(R.string.student_names));
        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }
}