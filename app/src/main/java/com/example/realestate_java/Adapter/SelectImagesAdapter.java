package com.example.realestate_java.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestate_java.R;
import com.example.realestate_java.model.SelectImagesAdapterModel;
import com.example.realestate_java.uitl.SelectImagesClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SelectImagesAdapter extends RecyclerView.Adapter<SelectImagesAdapter.MyViewHolder> {

    private static final String TAG = "SelectImagesAdapter";
    ArrayList<SelectImagesAdapterModel> imageList;

    public SelectImagesAdapter(ArrayList<SelectImagesAdapterModel> imageList) {
        this.imageList = imageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.selected_image_list_view);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(imageList.get(position).getUriArrayList()).into(holder.imageView);
        Log.d(TAG, "onBindViewHolder: "+imageList.get(position).getUriArrayList());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

}
