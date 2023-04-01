package com.example.skillmanager.Data.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MenteeWithAssignment {
    @Embedded
    public Mentee mentee;

    @Relation(
            parentColumn = "mentee_id",
            entityColumn = "assignment_id",
            associateBy = @Junction(MenteeAssignmentCrossRef.class)
    )
    public Assignment assignment;

    public String status;

    public Mentee getMentee() { return mentee; }

    public Assignment getAssignment() { return assignment; }

    public MenteeAssignmentCrossRef.Status getStatus() {
        return MenteeAssignmentCrossRef.Status.fromString(this.status);
    }

    public void setStatus(MenteeAssignmentCrossRef.Status status) {
        this.status = status.toString();
    }
}
