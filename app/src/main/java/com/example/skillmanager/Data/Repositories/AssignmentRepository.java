package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.AssignmentDAO;
import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.Entities.EmailReportItem;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeWithAssignment;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;
import java.util.concurrent.Future;

public class AssignmentRepository {
    private AssignmentDAO assignmentDAO;
    private MenteeAssignmentDAO menteeAssignmentDAO;
    public AssignmentRepository(SkillManagerDatabase db) {
        assignmentDAO = db.assignmentDAO();
        menteeAssignmentDAO = db.menteeAssignmentDAO();
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

    public LiveData<MenteeWithAssignment> getMenteeWithAssignment(long cycleId, long menteeId, long assignmentId) {
        return assignmentDAO.getMenteeWithAssignment(cycleId, menteeId, assignmentId);
    }

    public void insert(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> {
            try {
                assignmentDAO.insert(assignment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void update(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> assignmentDAO.update(assignment));
    }

    public void delete(Assignment assignment) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> assignmentDAO.delete(assignment));
    }

    public void updateMenteeAssignment(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeAssignmentDAO.update(menteeAssignmentCrossRef));
    }

    public void updateEmailSent(long cycleId, long menteeId, String dateString) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeAssignmentDAO.updateEmailSent(cycleId, menteeId, dateString));
    }

    public LiveData<List<EmailReportItem>> getEmailReportData() {
        return menteeAssignmentDAO.getEmailReportData();
    }
}
