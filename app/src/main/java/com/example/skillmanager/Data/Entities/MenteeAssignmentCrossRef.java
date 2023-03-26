package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
        tableName = "mentee_assignments",
        primaryKeys = {"mentee_id", "assignment_id"}
)
public class MenteeAssignmentCrossRef {

    @ColumnInfo(name = "mentee_id")
    private long menteeId;

    @ColumnInfo(name = "assignment_id")
    private long assignmentId;

    public long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(long menteeId) {
        this.menteeId = menteeId;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }
}
