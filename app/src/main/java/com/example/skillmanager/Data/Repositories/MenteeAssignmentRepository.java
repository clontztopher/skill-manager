package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;

import java.util.List;
import java.util.concurrent.Future;

public class MenteeAssignmentRepository {
    private MenteeAssignmentDAO menteeAssignmentDAO;

    public MenteeAssignmentRepository(Application application) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(application);
        menteeAssignmentDAO = db.menteeAssignmentDAO();
    }

    public Future<List<Mentee>> getMenteesForAssignment(long assignmentId) {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.findMenteesForAssignment(assignmentId));
    }

    public Future<?> insert(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.insert(menteeAssignmentCrossRef));
    }

    public Future<?> update(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.update(menteeAssignmentCrossRef));
    }

    public Future<?> delete(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> menteeAssignmentDAO.delete(menteeAssignmentCrossRef));
    }
}

