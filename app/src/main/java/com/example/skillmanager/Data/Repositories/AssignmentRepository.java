package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.AssignmentDAO;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;
import java.util.concurrent.Future;

public class AssignmentRepository {
    private AssignmentDAO assignmentDAO;
    public AssignmentRepository(Application application) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(application);
        assignmentDAO = db.assignmentDAO();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return assignmentDAO.getAllAssignments();
    }

    public Future<List<Assignment>> getAllAssignmentsSync() {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> assignmentDAO.getAllAssignmentsSync());
    }

    public LiveData<Assignment> findById(long id) {
        return assignmentDAO.findById(id);
    }

    public void insert(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> assignmentDAO.insert(assignment));
    }

    public void update(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> assignmentDAO.update(assignment));
    }

    public void delete(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> assignmentDAO.delete(assignment));
    }
}
