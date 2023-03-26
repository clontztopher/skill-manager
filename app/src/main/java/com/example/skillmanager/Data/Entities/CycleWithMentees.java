package com.example.skillmanager.Data.Entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CycleWithMentees {
    @Embedded
    public Cycle cycle;

    @Relation(
            parentColumn = "cycle_id",
            entityColumn = "mentee_id",
            associateBy = @Junction(MenteeCycleCrossRef.class)
    )
    public List<Mentee> mentees;

    public Cycle getCycle() {
        return cycle;
    }

    public List<Mentee> getMentees() {
        return mentees;
    }
}
