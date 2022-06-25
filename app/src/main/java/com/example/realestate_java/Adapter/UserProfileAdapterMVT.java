package com.example.realestate_java.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestate_java.R;
import com.example.realestate_java.model.Post;
import com.example.realestate_java.model.UserProfileAdapterModel;
import com.example.realestate_java.uitl.TimeAgo;
import com.example.realestate_java.uitl.UserProfileClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class UserProfileAdapterMVT extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "UserProfileAdapterMVT";

    public static final int VIEW_ONE_PROFILE = 1;
    public static final int VIEW_TWO_POSTS = 2;

    private static ArrayList<UserProfileAdapterModel> list;
    private static UserProfileClickListener listener;

    public UserProfileAdapterMVT(ArrayList<UserProfileAdapterModel> list, UserProfileClickListener listener) {
        UserProfileAdapterMVT.list = list;
        UserProfileAdapterMVT.listener = listener;
    }

    public static class ProfileDataView extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, email, phone;
        ImageView selectImage;
        Button logoutBtn;

        public ProfileDataView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roundedImageView);
            name = itemView.findViewById(R.id.tv_name);
            email = itemView.findViewById(R.id.tv_email);
            phone = itemView.findViewById(R.id.tv_phone);
            selectImage = itemView.findViewById(R.id.add_profile_image_btn);
            logoutBtn = itemView.findViewById(R.id.logOut);
        }

        void bind(int position) {
            UserProfileAdapterModel userProfile = list.get(position);
            Glide.with(itemView.getContext())
                    .load(userProfile.getUserProfileImage())
                    .placeholder(R.drawable.profile_placeholder)
                    .into(imageView);
            name.setText(userProfile.getUserName());
            email.setText(userProfile.getEmail());
            phone.setText(userProfile.getPhone());

            selectImage.setOnClickListener(view -> {
                listener.selectImage(position);
            });

            logoutBtn.setOnClickListener(view -> {
                listener.logOut(position);
            });
        }
    }

    public static class ProfilePostsView extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView category, subCategory, location, timeAgo;
        CardView cardView;

        public ProfilePostsView(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.roundedImageAdapter);
            category = itemView.findViewById(R.id.tv_category);
            subCategory = itemView.findViewById(R.id.subCatgory);
            location = itemView.findViewById(R.id.tv_location);
            cardView = itemView.findViewById(R.id.card_view);
            timeAgo = itemView.findViewById(R.id.tv_time_ago);
        }

        void bind(int position) {

            UserProfileAdapterModel userProfileAdapterModel = list.get(position);
            Post post = userProfileAdapterModel.getPost();
            Glide.with(itemView.getContext()).load(post.getPostImagesList().get(0)).into(imageView);
            category.setText(post.getPostCategory());
            subCategory.setText(post.getPostSubCategory());
            location.setText(post.getAddress());
            timeAgo.setText(new TimeAgo().getTimeAgo(Long.parseLong(post.getCreateDateTime())));
            Log.d(TAG, "bind: timestamp"+post.getCreateDateTime());
            Log.d(TAG, "bind: time ago - "+new TimeAgo().getTimeAgo(Long.parseLong(post.getCreateDateTime())));

            cardView.setOnClickListener(view -> {
                listener.onItemClick(position, view);
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_ONE_PROFILE) {
            return new ProfileDataView(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.user_profile_layout,
                            parent,
                            false));

        }

        return new ProfilePostsView(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_view_layout,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (list.get(position).getViewType() == VIEW_ONE_PROFILE) {
            (new ProfileDataView(holder.itemView)).bind(position);
        } else {
            (new ProfilePostsView(holder.itemView)).bind(position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }
}
