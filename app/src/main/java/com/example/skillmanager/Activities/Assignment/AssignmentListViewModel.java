package com.example.skillmanager.Activities.Assignment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;

import java.util.List;

public class AssignmentListViewModel extends AndroidViewModel {
    private LiveData<List<Assignment>> mAssignemnts;

    public AssignmentListViewModel(Application app) {
        super(app);
        AssignmentRepository assignmentRepository = new AssignmentRepository(app);
        mAssignemnts = assignmentRepository.getAllAssignments();
    }

    public LiveData<List<Assignment>> getAssignments() {
        return mAssignemnts;
    }
}
