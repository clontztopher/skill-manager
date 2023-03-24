package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;

import java.util.List;
import java.util.concurrent.Future;

public class MenteeAssignmentRepository {
    private MenteeAssignmentDAO menteeAssignmentDAO;

    public MenteeAssignmentRepository(Application application) {
        Database db = Database.getInstance(application);
        menteeAssignmentDAO = db.menteeAssignmentDAO();
    }

    public Future<List<Mentee>> getMenteesForAssignment(long assignmentId) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.findMenteesForAssignment(assignmentId));
    }

    public Future<List<Mentee>> getMenteesNotAssigned(long assignmentId) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.getMenteesNotAssigned(assignmentId));
    }

    public Future<?> removeFromAssignments(long menteeId) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.removeFromAssignments(menteeId));
    }

    public Future<?> insert(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.insert(menteeAssignmentCrossRef));
    }

    public Future<?> update(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.update(menteeAssignmentCrossRef));
    }

    public Future<?> delete(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return Database.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.delete(menteeAssignmentCrossRef));
    }
}

