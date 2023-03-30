package com.example.skillmanager.Data.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class MenteeWithAssignments {
    @Embedded
    public Mentee mentee;

    @Relation(
            parentColumn = "mentee_id",
            entityColumn = "assignment_id",
            associateBy = @Junction(MenteeAssignmentCrossRef.class)
    )
    public List<Assignment> assignments;

    public Mentee getMentee() { return mentee; }

    public List<Assignment> getAssignments() { return assignments; }
}
