package com.example.myapplication.models;

import java.io.Serializable;
import java.util.List;

public class Place implements Serializable {
    private String id;
    private String name;
    private String description;
    private List<Integer> imageResIds; // Updated to List for multiple photos
    private String phone;
    private String website;
    private String email;
    private float rating;
    private int reviewCount;
    private boolean isFavorite;
    private String category;



        public Place(String id, String name, String description, List<Integer> imageResIds,
                     String phone, String website, String email, float rating,
                     int reviewCount, boolean isFavorite, String category) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.imageResIds = imageResIds;
            this.phone = phone;
            this.website = website;
            this.email = email;
            this.rating = rating;
            this.reviewCount = reviewCount;
            this.isFavorite = isFavorite;
            this.category = category;
        }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Integer> getImageResIds() { return imageResIds; }
    public String getPhone() { return phone; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }
    public String getCategory() { return category; }
}