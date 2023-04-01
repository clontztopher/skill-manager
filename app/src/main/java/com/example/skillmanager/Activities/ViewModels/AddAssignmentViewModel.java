package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignment;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Data.Repositories.MenteeRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.List;

public class AddAssignmentViewModel extends AndroidViewModel {

    CycleRepository cycleRepository;
    AssignmentRepository assignmentRepository;
    MenteeRepository menteeRepository;

    public AddAssignmentViewModel(Application app) {
        super(app);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(app);
        cycleRepository = new CycleRepository(db);
        menteeRepository = new MenteeRepository(db);
        assignmentRepository = new AssignmentRepository(db);
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
