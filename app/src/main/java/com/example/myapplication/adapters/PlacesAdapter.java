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

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Place> places;
    private List<Place> placesFull; // Full list for filtering
    private SharedPreferences prefs;
    private Set<String> favorites;

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
        this.placesFull = new ArrayList<>(places); // Copy for filtering
        this.prefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE);
        this.favorites = new HashSet<>(prefs.getStringSet("favorite_places", new HashSet<>()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (places != null && position < places.size()) {
            Place place = places.get(position);
            holder.name.setText(place.getName());
            holder.description.setText(place.getDescription());
            holder.image.setImageResource(place.getImage());

            holder.favoriteIcon.setImageResource(favorites.contains(place.getId()) ?
                    R.drawable.ic_star_filled : R.drawable.ic_star_outline);

            holder.favoriteIcon.setOnClickListener(v -> {
                if (favorites.contains(place.getId())) {
                    favorites.remove(place.getId());
                    holder.favoriteIcon.setImageResource(R.drawable.ic_star_outline);
                } else {
                    favorites.add(place.getId());
                    holder.favoriteIcon.setImageResource(R.drawable.ic_star_filled);
                }
                prefs.edit().putStringSet("favorite_places", favorites).apply();
            });

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("place_name", place.getName());
                intent.putExtra("place_description", place.getDescription());
                intent.putExtra("place_image", place.getImage());
                intent.putExtra("place_phone", place.getPhoneNumber());
                intent.putExtra("place_website", place.getWebsite());
                intent.putExtra("place_email", place.getEmail());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (places != null) ? places.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    private Filter placeFilter = new Filter() {
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
            places.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView image, favoriteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.placeName);
            description = itemView.findViewById(R.id.placeDescription);
            image = itemView.findViewById(R.id.placeImage);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}