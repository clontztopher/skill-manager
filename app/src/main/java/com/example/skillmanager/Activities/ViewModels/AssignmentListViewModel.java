package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;

import java.util.List;

public class AssignmentListViewModel extends AndroidViewModel {
    private LiveData<List<Assignment>> mAssignments;

    public AssignmentListViewModel(Application app) {
        super(app);
        AssignmentRepository assignmentRepository = new AssignmentRepository(app);
        mAssignments = assignmentRepository.getAllAssignments();
    }

    public LiveData<List<Assignment>> getAssignments() {
        return mAssignments;
    }
}
