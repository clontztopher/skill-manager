package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignment;
import com.example.skillmanager.Data.Entities.MenteeWithAssignments;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Data.Repositories.MenteeRepository;

import java.util.List;

public class AddAssignmentViewModel extends AndroidViewModel {

    CycleRepository cycleRepository;
    AssignmentRepository assignmentRepository;
    MenteeRepository menteeRepository;

    public AddAssignmentViewModel(Application app) {
        super(app);
        cycleRepository = new CycleRepository(app);
        menteeRepository = new MenteeRepository(app);
        assignmentRepository = new AssignmentRepository(app);
    }

    public LiveData<List<Assignment>> getAssignments() {
        return assignmentRepository.getAllAssignments();
    }

    public LiveData<List<Mentee>> getMentees() {
        return menteeRepository.getAllMentees();
    }

    public void saveAssignment(long cycleId, long menteeId, long assignmentId) {
        cycleRepository.addAssignment(new MenteeAssignmentCrossRef(cycleId, menteeId, assignmentId));
    }

    public LiveData<MenteeWithAssignment> getMenteeWithAssignment(long cycleId, long menteeId, long assignmentId) {
        return assignmentRepository.getMenteeWithAssignment(cycleId, menteeId, assignmentId);
    }

    public void updateAssignment(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        assignmentRepository.updateMenteeAssignment(menteeAssignmentCrossRef);
    }
}
