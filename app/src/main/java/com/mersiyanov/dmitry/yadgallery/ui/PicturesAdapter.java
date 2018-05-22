package com.mersiyanov.dmitry.yadgallery.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mersiyanov.dmitry.yadgallery.R;
import com.mersiyanov.dmitry.yadgallery.pojo.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.ViewHolder> {

    private List<Item> itemList;
    private OnPictureClickListener onPictureClickListener;

    public PicturesAdapter(List<Item> itemList, OnPictureClickListener onPictureClickListener) {
        this.itemList = itemList;
        this.onPictureClickListener = onPictureClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.picture_item_view, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Item image = itemList.get(position);

        Picasso.get().load(image.getFile()).resize(550, 550).centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.v("Picasso","Image was fetched from cache");
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(image.getFile()).resize(550, 550)
                                .centerCrop()
                                .into(holder.imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.v("Picasso","Image was fetched from internet");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPictureClickListener != null) {
                    onPictureClickListener.onPictureClick(image);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    public interface OnPictureClickListener {
        void onPictureClick(Item image);
    }
}
