package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context context;
    private List<Integer> images;
    private OnPhotoClickListener listener;
    private boolean isFullScreen;
    private boolean isHomePage;

    public interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }

    public PhotoAdapter(Context context, List<Integer> images, OnPhotoClickListener listener, boolean isFullScreen, boolean isHomePage) {
        this.context = context;
        this.images = images;
        this.listener = listener;
        this.isFullScreen = isFullScreen;
        this.isHomePage = isHomePage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = isFullScreen ? R.layout.item_full_screen : R.layout.item_photo;
        View view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int imageRes = images.get(position % images.size());
        holder.imageView.setImageResource(imageRes);

        // Apply right-to-left slide for homepage carousel
        if (!isFullScreen && isHomePage) {
            holder.imageView.setAlpha(0f);
            holder.imageView.setTranslationX(100f);
            holder.imageView.animate()
                    .alpha(1f)
                    .translationX(0f)
                    .setDuration(600)
                    .withEndAction(() -> {
                        holder.imageView.setAlpha(1f);
                        holder.imageView.setTranslationX(0f);
                    })
                    .start();
        }

        // Set click listener for non-fullscreen, non-homepage views (PlaceDetailsActivity)
        if (!isFullScreen && !isHomePage) {
            holder.itemView.setOnClickListener(v -> listener.onPhotoClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail);
        }
    }
}