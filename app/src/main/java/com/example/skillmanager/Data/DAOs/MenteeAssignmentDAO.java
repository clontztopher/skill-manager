package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.skillmanager.Data.Entities.EmailReportItem;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;

import java.util.List;

@Dao
public interface MenteeAssignmentDAO extends BaseDAO<MenteeAssignmentCrossRef> {
    @Query("UPDATE mentee_assignments SET email_sent_date = :dateString WHERE cycle_id = :cycleId AND mentee_id = :menteeId")
    public void updateEmailSent(long cycleId, long menteeId, String dateString);

    @Query("SELECT DISTINCT cycles.name as mCycleName, mentees.name as mMenteeName, mentee_assignments.email_sent_date as mEmailDate " +
            "FROM mentee_assignments " +
            "INNER JOIN cycles, mentees " +
            "ON mentee_assignments.cycle_id = cycles.cycle_id " +
            "AND mentee_assignments.mentee_id = mentees.mentee_id")
    public LiveData<List<EmailReportItem>> getEmailReportData();
}
