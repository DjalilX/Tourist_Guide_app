package com.example.myapplication.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String name, description, phoneNumber, website, email;
    private int image; // Added the missing field

    public Place(String name, String description, int image, String phoneNumber, String website, String email) {
        this.name = name;
        this.description = description;
        this.image = image; // Assign the image parameter to the field
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.email = email;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getImage() { return image; } // Return the image field
    public String getPhoneNumber() { return phoneNumber; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
}