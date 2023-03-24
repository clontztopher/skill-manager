package com.example.skillmanager.Activities.Assignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.R;

public class AssignmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Assignment mAssignment;
    private TextView mAssignmentNameTextView;
    private TextView mAssignmentStartTextView;
    private TextView mAssignmentEndTextView;

    public AssignmentHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_assignment, parent, false));
        itemView.setOnClickListener(this);
        mAssignmentNameTextView = itemView.findViewById(R.id.assignmentTitleColumn);
        mAssignmentStartTextView = itemView.findViewById(R.id.assignmentStartColumn);
        mAssignmentEndTextView = itemView.findViewById(R.id.assignmentEndColumn);
    }

    public void bind(Assignment assignment) {
        mAssignment = assignment;
        mAssignmentNameTextView.setText(mAssignment.getTitle());
        mAssignmentStartTextView.setText(mAssignment.getStartDateString());
        mAssignmentEndTextView.setText(mAssignment.getEndDateString());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), AssignmentDetailActivity.class);
        intent.putExtra(AssignmentDetailActivity.EXTRA_ASSIGNMENT_ID, mAssignment.getAssignmentId());
        view.getContext().startActivity(intent);
    }
}
