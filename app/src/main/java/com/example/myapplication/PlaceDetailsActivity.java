package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class PlaceDetailsActivity extends BaseActivity {

    private static final String TAG = "PlaceDetailsActivity";

    private ImageView placeImage;
    private TextView placeName, placeDescription;
    private Button btnCall, btnWebsite;
    private String phoneNumber, websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        placeImage = findViewById(R.id.imageViewPlace);
        placeName = findViewById(R.id.textViewPlaceName);
        placeDescription = findViewById(R.id.textViewPlaceDescription);
        btnCall = findViewById(R.id.btnCall);
        btnWebsite = findViewById(R.id.btnWebsite);

        String name = getIntent().getStringExtra("place_name");
        String description = getIntent().getStringExtra("place_description");
        int imageRes = getIntent().getIntExtra("place_image", R.drawable.ic_launcher_foreground);
        phoneNumber = getIntent().getStringExtra("place_phone");
        websiteUrl = getIntent().getStringExtra("place_website");

        if (name == null || description == null) {
            Log.e(TAG, "Error: Missing place details");
            finish();
            return;
        }

        placeName.setText(name);
        placeDescription.setText(description);
        placeImage.setImageResource(imageRes);

        btnCall.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                Log.e(TAG, "Error: Phone number is missing!");
            }
        });

        btnWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                startActivity(webIntent);
            } else {
                Log.e(TAG, "Error: Website URL is missing!");
            }
        });

        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }
}