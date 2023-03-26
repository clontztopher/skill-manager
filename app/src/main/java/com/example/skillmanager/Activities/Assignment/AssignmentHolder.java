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
    private TextView titleView;
    private TextView topicView;
    private TextView typeView;

    public AssignmentHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_assignment, parent, false));
        itemView.setOnClickListener(this);
        titleView = itemView.findViewById(R.id.assignmentTitleColumn);
        typeView = itemView.findViewById(R.id.assignmentTypeColumn);
        topicView = itemView.findViewById(R.id.assignmentTopicColumn);
    }

    public void bind(Assignment assignment) {
        mAssignment = assignment;
        titleView.setText(mAssignment.getTitle());
        typeView.setText(mAssignment.getType().toString());
        topicView.setText(mAssignment.getTopic());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), AssignmentDetailActivity.class);
        intent.putExtra(AssignmentDetailActivity.EXTRA_ASSIGNMENT_ID, mAssignment.getAssignmentId());
        view.getContext().startActivity(intent);
    }
}
