package com.example.skillmanager.Activities.Reports;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.skillmanager.Data.Entities.EmailReportItem;

public class ReportAdapter extends ListAdapter<EmailReportItem, ReportHolder> {
    public ReportAdapter() {
        super(ReportAdapter.DIFF_CALLBACK);
    }

    @Override
    public ReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ReportHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(ReportHolder reportHolder, int position) {
        reportHolder.bind(getItem(position));
    }

    private static final DiffUtil.ItemCallback<EmailReportItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<EmailReportItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull EmailReportItem oldItem, @NonNull EmailReportItem newItem) {
            return (oldItem.getMenteeName()+oldItem.getCycleName()+oldItem.getEmailDate()).equals(newItem.getMenteeName()+newItem.getCycleName()+newItem.getEmailDate());
        }

        @Override
        public boolean areContentsTheSame(@NonNull EmailReportItem oldItem, @NonNull EmailReportItem newItem) {
            return (oldItem.getMenteeName()+oldItem.getCycleName()+oldItem.getEmailDate()).equals(newItem.getMenteeName()+newItem.getCycleName()+newItem.getEmailDate());
        }
    };
}
