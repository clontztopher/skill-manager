package com.example.skillmanager.Activities.Mentee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.R;

public class MenteeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Mentee mMentee;
    private final TextView mMenteeNameTextView;
    private final TextView mMenteeEmailTextView;
    private final TextView mMenteePhoneTextView;
    public MenteeHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_mentee, parent, false));
        itemView.setOnClickListener(this);
        mMenteeNameTextView = itemView.findViewById(R.id.menteeListName);
        mMenteeEmailTextView = itemView.findViewById(R.id.menteeListEmail);
        mMenteePhoneTextView = itemView.findViewById(R.id.menteeListPhone);
    }

    public void bind(Mentee mentee) {
        mMentee = mentee;
        mMenteeNameTextView.setText(mMentee.getName());
        mMenteeEmailTextView.setText(mMentee.getEmail());
        mMenteePhoneTextView.setText(mMentee.getPhone());
    }

    @Override
    public void onClick(View view) {

    }
}
