package com.example.realestate_java.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HouseViewHolder> {

    ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        TextView category, subCategory, location;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roundedImageAdapter);
            category = itemView.findViewById(R.id.tv_category);
            subCategory = itemView.findViewById(R.id.subCatgory);
            location = itemView.findViewById(R.id.tv_location);
        }

        void bind(Post post) {
            Glide.with(itemView.getContext()).load(post.getPostImagesList().get(0)).into(imageView);
            category.setText(post.getPostCategory());
            subCategory.setText(post.getPostSubCategory());
            location.setText(post.getAddress());
        }
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HouseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        holder.bind(postArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }
}
