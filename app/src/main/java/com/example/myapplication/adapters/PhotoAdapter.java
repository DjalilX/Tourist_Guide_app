package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context context;
    private List<Integer> imageResIds;
    private OnPhotoClickListener listener;
    private boolean isFullScreen; // New flag to determine layout

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }

    public PhotoAdapter(Context context, List<Integer> imageResIds, OnPhotoClickListener listener, boolean isFullScreen) {
        this.context = context;
        this.imageResIds = imageResIds;
        this.listener = listener;
        this.isFullScreen = isFullScreen;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = isFullScreen ? R.layout.item_full_photo : R.layout.item_photo;
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        int imageResId = imageResIds.get(position);
        holder.imageView.setImageResource(imageResId);
        if (!isFullScreen) { // Only set click listener for thumbnails
            holder.itemView.setOnClickListener(v -> listener.onPhotoClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return imageResIds.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(isFullScreenView(itemView) ? R.id.fullPhoto : R.id.thumbnail);
        }

        private static boolean isFullScreenView(View view) {
            return view.getId() == R.id.fullPhoto;
        }
    }
}