package com.example.myapplication.models;

import java.io.Serializable;

public class Place implements Serializable { // ✅ Add "implements Serializable"
    private String name, description, phoneNumber, website;
    private int image;

    public Place(String name, String description, int image, String phoneNumber, String website) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getImage() { return image; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getWebsite() { return website; }
}
