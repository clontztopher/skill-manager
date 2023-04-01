package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.List;

public class AssignmentListViewModel extends AndroidViewModel {
    private LiveData<List<Assignment>> mAssignments;

    public AssignmentListViewModel(Application app) {
        super(app);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(app);
        AssignmentRepository assignmentRepository = new AssignmentRepository(db);
        mAssignments = assignmentRepository.getAllAssignments();
    }

    public LiveData<List<Assignment>> getAssignments() {
        return mAssignments;
    }
}
