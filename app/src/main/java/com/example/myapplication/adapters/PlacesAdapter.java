package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.PlaceDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Place;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private Context context;
    private List<Place> places;

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (places != null && position < places.size()) { // ✅ Prevents crash if places list is empty
            Place place = places.get(position);
            holder.name.setText(place.getName());
            holder.description.setText(place.getDescription());
            holder.image.setImageResource(place.getImage());

            // ✅ Handle item clicks to open PlaceDetailsActivity
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PlaceDetailsActivity.class);
                intent.putExtra("place_name", place.getName());
                intent.putExtra("place_description", place.getDescription());
                intent.putExtra("place_image", place.getImage());
                intent.putExtra("place_phone", place.getPhoneNumber());
                intent.putExtra("place_website", place.getWebsite());
                intent.putExtra("place_email", place.getEmail()); // Add email
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (places != null) ? places.size() : 0; // ✅ Prevents crash if places list is null
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.placeName);
            description = itemView.findViewById(R.id.placeDescription);
            image = itemView.findViewById(R.id.placeImage);
        }
    }
}
