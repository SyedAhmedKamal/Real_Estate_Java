package com.example.realestate_java.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.realestate_java.R;
import com.example.realestate_java.model.SliderItem;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private static final String TAG = "SliderAdapter";

    private ArrayList<SliderItem> sliderItemList;
    private ViewPager2 viewPager2;

    public SliderAdapter(ArrayList<SliderItem> sliderItemList) {
        this.sliderItemList = sliderItemList;
        this.viewPager2 = viewPager2;
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        TextView textView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_slider);
            textView = itemView.findViewById(R.id.tv_slider);
        }

        void bind(SliderItem sliderItem) {
            imageView.setImageResource(sliderItem.getImageID());
            textView.setText(sliderItem.getText());
        }
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.slider_item_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.bind(sliderItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItemList.size();
    }

}