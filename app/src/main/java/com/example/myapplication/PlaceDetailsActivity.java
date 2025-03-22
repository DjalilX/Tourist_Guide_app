package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class PlaceDetailsActivity extends BaseActivity {

    private static final String TAG = "PlaceDetailsActivity";

    private ImageView placeImage;
    private TextView placeName, placeDescription;
    private Button btnCall, btnSms, btnEmail, btnWebsite, btnMap;
    private String phoneNumber, websiteUrl, email;

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
        btnSms = findViewById(R.id.btnSms);
        btnEmail = findViewById(R.id.btnEmail);
        btnWebsite = findViewById(R.id.btnWebsite);
        btnMap = findViewById(R.id.btnMap);

        String name = getIntent().getStringExtra("place_name");
        String description = getIntent().getStringExtra("place_description");
        int imageRes = getIntent().getIntExtra("place_image", R.drawable.ic_launcher_foreground);
        phoneNumber = getIntent().getStringExtra("place_phone");
        websiteUrl = getIntent().getStringExtra("place_website");
        email = getIntent().getStringExtra("place_email");

        if (name == null || description == null) {
            Log.e(TAG, "Error: Missing place details");
            Toast.makeText(this, "Error: Place details unavailable", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Dialing " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        btnSms.setOnClickListener(v -> {
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("sms:" + phoneNumber));
                smsIntent.putExtra("sms_body", "Hello, I’d like more info about " + name);
                startActivity(smsIntent);
                Toast.makeText(this, "Opening SMS for " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No phone number available", Toast.LENGTH_SHORT).show();
            }
        });

        btnEmail.setOnClickListener(v -> {
            if (email != null && !email.isEmpty()) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry about " + name);
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I’m interested in learning more about " + name + ".");
                startActivity(emailIntent);
                Toast.makeText(this, "Sending email about " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No email available", Toast.LENGTH_SHORT).show();
            }
        });

        btnWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                startActivity(webIntent);
                Toast.makeText(this, "Opening website for " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No website available", Toast.LENGTH_SHORT).show();
            }
        });

        btnMap.setOnClickListener(v -> {
            String geoUri = "geo:0,0?q=" + Uri.encode(name + ", Algiers");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
            startActivity(mapIntent);
            Toast.makeText(this, "Showing " + name + " on map", Toast.LENGTH_SHORT).show();
        });

        Log.d(TAG, "Current locale: " + getResources().getConfiguration().getLocales().get(0));
    }
}