package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
        tableName = "mentee_cycles",
        primaryKeys = {"menteeId", "cycleId"}
)
public class MenteeCycleCrossRef {

    @ColumnInfo(name = "menteeId")
    private long menteeId;

    @ColumnInfo(name = "cycleId")
    private long cycleId;

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
