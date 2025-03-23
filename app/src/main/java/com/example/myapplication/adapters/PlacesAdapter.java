package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.PlaceDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Place;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> implements Filterable {

    private Context context;
    private List<Place> places;
    private List<Place> placesFull; // For filtering
    private SharedPreferences prefs;

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
        this.placesFull = new ArrayList<>(places);
        this.prefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        Place place = places.get(position);

        holder.placeName.setText(place.getName());
        holder.placeDescription.setText(place.getDescription());
        holder.placeImage.setImageResource(place.getImageResId());
        holder.ratingBar.setRating(place.getRating());
        holder.reviewCount.setText(context.getString(R.string.review_count, place.getReviewCount()));
        holder.favoriteIcon.setImageResource(place.isFavorite() ? R.drawable.ic_star_filled : R.drawable.ic_star_outline);

        holder.favoriteIcon.setOnClickListener(v -> {
            boolean isFavorite = !place.isFavorite();
            place.setFavorite(isFavorite);
            holder.favoriteIcon.setImageResource(isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_outline);

            Set<String> favorites = new HashSet<>(prefs.getStringSet("favorite_places", new HashSet<>()));
            if (isFavorite) {
                favorites.add(place.getId());
            } else {
                favorites.remove(place.getId());
            }
            prefs.edit().putStringSet("favorite_places", favorites).apply();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaceDetailsActivity.class);
            intent.putExtra("place_name", place.getName());
            intent.putExtra("place_description", place.getDescription());
            intent.putExtra("place_image", place.getImageResId());
            intent.putExtra("place_phone", place.getPhone());
            intent.putExtra("place_website", place.getWebsite());
            intent.putExtra("place_email", place.getEmail());
            intent.putExtra("place_rating", place.getRating());
            intent.putExtra("place_review_count", place.getReviewCount());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Place> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(placesFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Place place : placesFull) {
                        if (place.getName().toLowerCase().contains(filterPattern) ||
                                place.getDescription().toLowerCase().contains(filterPattern)) {
                            filteredList.add(place);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                places.clear();
                places.addAll((List<Place>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView placeImage, favoriteIcon;
        TextView placeName, placeDescription, reviewCount;
        RatingBar ratingBar;

        PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeName = itemView.findViewById(R.id.placeName);
            placeDescription = itemView.findViewById(R.id.placeDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            reviewCount = itemView.findViewById(R.id.reviewCount);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}