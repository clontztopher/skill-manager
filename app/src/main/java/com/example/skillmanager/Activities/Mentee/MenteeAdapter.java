package com.example.skillmanager.Activities.Mentee;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Mentee;

import java.util.List;

public class MenteeAdapter extends ListAdapter<Mentee, MenteeHolder> {
    public MenteeAdapter() {
        super(MenteeAdapter.DIFF_CALLBACK);
    }

    @Override
    public MenteeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MenteeHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(MenteeHolder menteeHolder, int position) {
        menteeHolder.bind(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Mentee> DIFF_CALLBACK = new DiffUtil.ItemCallback<Mentee>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mentee oldItem, @NonNull Mentee newItem) {
            return oldItem.getMenteeId() == newItem.getMenteeId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mentee oldItem, @NonNull Mentee newItem) {
            return oldItem.getEmail().equals(newItem.getEmail());
        }
    };
}
