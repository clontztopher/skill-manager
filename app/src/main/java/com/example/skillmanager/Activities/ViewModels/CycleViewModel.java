package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignments;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Data.Repositories.MenteeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;

public class CycleViewModel extends AndroidViewModel {
    private CycleRepository cycleRepository;
    private MenteeRepository menteeRepository;
    private AssignmentRepository assignmentRepository;

    public CycleViewModel(Application app) {
        super(app);
        cycleRepository = new CycleRepository(app);
        menteeRepository = new MenteeRepository(app);
        assignmentRepository = new AssignmentRepository(app);
    }
    public LiveData<Cycle> getCycle(long id) {
        return cycleRepository.getCycleById(id);
    }

    public LiveData<List<MenteeWithAssignments>> getMenteesForCycle(long cycleId) {
        return menteeRepository.getMenteesForCycle(cycleId);
    }

    public void removeAssignment(long cycleId, long menteeId, long assignmentId) {
        cycleRepository.removeAssignment(new MenteeAssignmentCrossRef(cycleId, menteeId, assignmentId));
    }

    public void updateEmailSent(long cycleId, long menteeId) {
        String dateString = LocalDateTime.now().toString();
        assignmentRepository.updateEmailSent(cycleId, menteeId, dateString);
    }

    public void insert(Cycle cycle) {
        cycleRepository.insert(cycle);
    }

    public void update(Cycle cycle) {
        cycleRepository.update(cycle);
    }

    public void delete(Cycle cycle) {
        cycleRepository.delete(cycle);
    }

    @Override
    protected void onCleared() {
        cycleRepository = null;
    }
}
