package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.EmailReportItem;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.List;

public class ReportsViewModel extends AndroidViewModel {
    AssignmentRepository assignmentRepository;

    public ReportsViewModel(Application app) {
        super(app);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(app);
        assignmentRepository = new AssignmentRepository(db);
    }

    public LiveData<List<EmailReportItem>> getEmailReportData() {
        return assignmentRepository.getEmailReportData();
    }
}
