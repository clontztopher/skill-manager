package com.example.skillmanager.Activities.Cycle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.R;

public class CycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Cycle mCycle;
    private final TextView mCycleNameTextView;
    private final TextView mCycleStartTextView;
    private final TextView mCycleEndTextView;

    public CycleHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_cycle, parent, false));
        itemView.setOnClickListener(this);
        mCycleNameTextView = itemView.findViewById(R.id.cycleName);
        mCycleStartTextView = itemView.findViewById(R.id.cycleStart);
        mCycleEndTextView = itemView.findViewById(R.id.cycleEnd);
    }

    public void bind(Cycle cycle) {
        mCycle = cycle;
        mCycleNameTextView.setText(mCycle.getDisplayName());
        mCycleStartTextView.setText(mCycle.getStartDateString());
        mCycleEndTextView.setText(mCycle.getEndDateString());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), CycleDetailsActivity.class);
        intent.putExtra(CycleDetailsActivity.EXTRA_TERM_ID, mCycle.getId());
        view.getContext().startActivity(intent);
    }
}
