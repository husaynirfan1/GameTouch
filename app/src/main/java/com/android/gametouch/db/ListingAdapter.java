package com.android.gametouch.db;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gametouch.R;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ViewHolder>{

    // creating a variable for on item click liste
    //ner.
    private OnItemClickListener listener;
    private OnBookmarkedListener onBookmarkedListener;
    private AsyncListDiffer<ScoreModal1> mAsyncListDiffer;
    private int bpos;

    public ListingAdapter() {
        DiffUtil.ItemCallback<ScoreModal1> diffUtilCallback = new DiffUtil.ItemCallback<ScoreModal1>() {

            @Override
            public boolean areItemsTheSame(@NonNull ScoreModal1 newModal, @NonNull ScoreModal1 oldModal) {
                return newModal.getUsername().equals(oldModal.getUsername());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ScoreModal1 newItem, @NonNull ScoreModal1 oldItem) {
                return newItem.getLevel().equals(oldItem.getLevel()) &&
                        newItem.getScore().equals(oldItem.getScore());
            }
        };
        mAsyncListDiffer = new AsyncListDiffer<>(this, diffUtilCallback);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingAdapter.ViewHolder holder, int position) {
        ScoreModal1 modal = mAsyncListDiffer.getCurrentList().get(position);

        String username = modal.getUsername();
        String score = modal.getScore();

        holder.usernameTV.setText(username);
        holder.scoreTV.setText(score);
        holder.numberPos.setText(String.valueOf((position+1)+". "));
    }

    @Override
    public int getItemCount() {
        return mAsyncListDiffer.getCurrentList().size();
    }

    public void setOnBookmarkedListener(OnBookmarkedListener onBookmarkedListener) {
        this.onBookmarkedListener = onBookmarkedListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void updateList(List<ScoreModal1> modals) {
        mAsyncListDiffer.submitList(modals);
    }

    public interface OnItemClickListener {
        void onItemClick(ScoreModal1 model, View view);
    }

    public interface OnBookmarkedListener {
        void onBookmarkedListener(ScoreModal1 modal, CheckBox checkBox);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView usernameTV, scoreTV, numberPos;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            usernameTV = itemView.findViewById(R.id.usernameTV);
            scoreTV = itemView.findViewById(R.id.scoreTV);
            numberPos = itemView.findViewById(R.id.numberPos);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mAsyncListDiffer.getCurrentList().get(position), usernameTV);
                    }
                }
            });


            bpos = getAdapterPosition();

        }
    }

}
