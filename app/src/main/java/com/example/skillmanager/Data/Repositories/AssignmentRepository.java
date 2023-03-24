package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.AssignmentDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;
import java.util.concurrent.Future;

public class AssignmentRepository {
    private AssignmentDAO assignmentDAO;
    public AssignmentRepository(Application application) {
        Database db = Database.getInstance(application);
        assignmentDAO = db.assignmentDAO();
    }

    public LiveData<List<Assignment>> getAllAssignments() {
        return assignmentDAO.getAllAssignments();
    }

    public LiveData<Assignment> findAssignmentById(long id) {
        return assignmentDAO.findAssignmentById(id);
    }
    
    public LiveData<List<Assignment>> getAssignmentsByCycle(long cycleId) {
        return assignmentDAO.getAssignmentsByCycle(cycleId);
    }

    public void insert(Assignment assignment) {
        Database.databaseWriteExecutor.execute(() -> assignmentDAO.insertAssignment(assignment));
    }

    public void update(Assignment assignment) {
        Database.databaseWriteExecutor.execute(() -> assignmentDAO.updateAssignment(assignment));
    }

    public void delete(Assignment assignment) {
        Database.databaseWriteExecutor.execute(() -> assignmentDAO.deleteAssignment(assignment));
    }
}
