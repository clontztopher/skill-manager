package com.example.skillmanager.Activities.Reports;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.EmailReportItem;
import com.example.skillmanager.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportHolder extends RecyclerView.ViewHolder {
    private EmailReportItem emailReportItem;
    private TextView cycleNameView;
    private TextView menteeNameView;
    private TextView emailDateView;

    public ReportHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.list_item_email_report_item, parent, false));
        cycleNameView = itemView.findViewById(R.id.reportCycleColumn);
        menteeNameView = itemView.findViewById(R.id.reportMenteeColumn);
        emailDateView = itemView.findViewById(R.id.reportDateColumn);
    }

    public void bind(EmailReportItem emailReportItem) {
        this.emailReportItem = emailReportItem;
        String formattedDate;
        if (emailReportItem.getEmailDate() != null) {
            String dateString = emailReportItem.getEmailDate();
            LocalDateTime emailDT = LocalDateTime.parse(dateString);
            formattedDate = emailDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            formattedDate = "Not Sent";
        }
        cycleNameView.setText(emailReportItem.getCycleName());
        menteeNameView.setText(emailReportItem.getMenteeName());
        emailDateView.setText(formattedDate);
    }
}
