package com.example.realestate_java.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.uitl.PostClickListener;
import com.example.realestate_java.uitl.TimeAgo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HouseViewHolder> {

    private static final String TAG = "PostAdapter";

    ArrayList<Post> postArrayList;
    private static PostClickListener listener;

    public PostAdapter(ArrayList<Post> postArrayList, PostClickListener listener) {
        this.postArrayList = postArrayList;
        PostAdapter.listener = listener;
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        TextView category, subCategory, location, timeAgo;
        CardView cardView;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roundedImageAdapter);
            category = itemView.findViewById(R.id.tv_category);
            subCategory = itemView.findViewById(R.id.subCatgory);
            location = itemView.findViewById(R.id.tv_location);
            timeAgo = itemView.findViewById(R.id.tv_time_ago);
            cardView = itemView.findViewById(R.id.card_view);

        }

        void bind(Post post) {
            Glide.with(itemView.getContext()).load(post.getPostImagesList().get(0)).into(imageView);
            category.setText(post.getPostCategory());
            subCategory.setText(post.getPostSubCategory());
            location.setText(post.getAddress());
            timeAgo.setText(new TimeAgo().getTimeAgo(Long.parseLong(post.getCreateDateTime())));
            Log.d(TAG, "bind: timestamp"+post.getCreateDateTime());
            Log.d(TAG, "bind: time ago - "+new TimeAgo().getTimeAgo(Long.parseLong(post.getCreateDateTime())));

            cardView.setOnClickListener(view -> {
                listener.onItemClick(post, view);
            });
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
