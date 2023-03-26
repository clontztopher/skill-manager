package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(
        tableName = "mentee_cycles",
        primaryKeys = {"mentee_id", "cycle_id"}
)
public class MenteeCycleCrossRef {

    @ColumnInfo(name = "mentee_id")
    public long menteeId;

    @ColumnInfo(name = "cycle_id")
    public long cycleId;

    public MenteeCycleCrossRef() {}

    public MenteeCycleCrossRef(long menteeId, long cycleId) {
        this.menteeId = menteeId;
        this.cycleId = cycleId;
    }

    public long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(long menteeId) {
        this.menteeId = menteeId;
    }

    public long getCycleId() {
        return cycleId;
    }

    public void setCycleId(long cycleId) {
        this.cycleId = cycleId;
    }
}
