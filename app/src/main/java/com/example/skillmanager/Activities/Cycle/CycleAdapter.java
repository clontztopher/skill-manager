package com.example.skillmanager.Activities.Cycle;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Cycle;

import java.util.List;

public class CycleAdapter extends ListAdapter<Cycle, CycleHolder> {
    public CycleAdapter() {
        super(CycleAdapter.DIFF_CALLBACK);
    }
    @Override
    public CycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CycleHolder(layoutInflater, parent);
    }
    @Override
    public void onBindViewHolder(CycleHolder cycleHolder, int position) {
        cycleHolder.bind(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Cycle> DIFF_CALLBACK = new DiffUtil.ItemCallback<Cycle>() {
        @Override
        public boolean areItemsTheSame(@NonNull Cycle oldItem, @NonNull Cycle newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cycle oldItem, @NonNull Cycle newItem) {
            return oldItem.getDisplayName().equals(newItem.getDisplayName());
        }
    };
}
