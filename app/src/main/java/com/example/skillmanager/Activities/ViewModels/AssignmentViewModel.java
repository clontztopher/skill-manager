package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;

public class AssignmentViewModel extends AndroidViewModel {
    private AssignmentRepository assignmentRepository;

    public AssignmentViewModel(Application app) {
        super(app);
        assignmentRepository = new AssignmentRepository(app);
    }

    public LiveData<Assignment> findById(long id) {
        return assignmentRepository.findById(id);
    }

    public void insert(Assignment assignment) {
        assignmentRepository.insert(assignment);
    }

    public void update(Assignment assignment) {
        assignmentRepository.update(assignment);
    }

    public void delete(Assignment assignment) {
        assignmentRepository.delete(assignment);
    }
}
