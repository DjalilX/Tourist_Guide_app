package com.example.myapplication.models;

import java.io.Serializable;

public class Place implements Serializable {
    private String id; // Unique identifier for favorites
    private String name, description, phoneNumber, website, email;
    private int image;

    public Place(String id, String name, String description, int image, String phoneNumber, String website, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getImage() { return image; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
}