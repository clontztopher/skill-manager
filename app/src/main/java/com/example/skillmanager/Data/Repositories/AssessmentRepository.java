package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import com.example.skillmanager.Data.DAOs.AssessmentDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Assessment;

import java.util.List;
import java.util.concurrent.Future;

public class AssessmentRepository {
    private AssessmentDAO assessmentDAO;

    public AssessmentRepository(Application application) {
        Database db = Database.getInstance(application);
        //assessmentDAO = db.assessmentDAO();
    }

    public Future<List<Assessment>> getAllAssessments() {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.getAllAssessments());
    }

    public Future<List<Assessment>> getAssessmentsForAssignment(long assignmentId) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.getAssessmentsForAssignment(assignmentId));
    }

    public Future<Assessment> findAssessmentById(long id) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.findAssessmentById(id));
    }

    public Future<?> insert(Assessment assessment) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.insertAssessment(assessment));
    }

    public Future<?> update(Assessment assessment) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.updateAssessment(assessment));
    }

    public Future<?> delete(Assessment assessment) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.deleteAssessment(assessment));
    }

    public Future<?> deleteAssignmentAssessments(long assignmentId) {
        return Database.databaseWriteExecutor.submit(() -> assessmentDAO.deleteAssessmentsForAssignment(assignmentId));
    }
}
