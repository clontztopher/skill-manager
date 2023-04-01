package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Repositories.AssignmentRepository;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Data.Repositories.MenteeRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CycleViewModel extends AndroidViewModel {
    private CycleRepository cycleRepository;
    private MenteeRepository menteeRepository;
    private AssignmentRepository assignmentRepository;

    public CycleViewModel(Application app) {
        super(app);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(app);
        cycleRepository = new CycleRepository(db);
        menteeRepository = new MenteeRepository(db);
        assignmentRepository = new AssignmentRepository(db);
    }
    public LiveData<Cycle> getCycle(long id) {
        return cycleRepository.getCycleById(id);
    }

    public LiveData<Map<Mentee, List<Assignment>>> getMenteesForCycle(long cycleId) {
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
