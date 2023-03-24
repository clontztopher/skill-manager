package com.example.skillmanager.Activities.Assignment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;

public class AssignmentAdapter extends ListAdapter<Assignment, AssignmentHolder> {
    public AssignmentAdapter() {
        super(AssignmentAdapter.DIFF_CALLBACK);
    }

    @Override
    public AssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new AssignmentHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(AssignmentHolder assignmentHolder, int position) {
        assignmentHolder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<Assignment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Assignment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem.getAssignmentId() == newItem.getAssignmentId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assignment oldItem, @NonNull Assignment newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
}
