package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignments")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private long assignmentId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "start_date")
    private String mStartDateString;

    @ColumnInfo(name = "end_date")
    private String mEndDateString;

    @ColumnInfo(name = "status")
    private String mStatus;

    @ColumnInfo(name = "cycle_id")
    private long mCycleId;

    @ColumnInfo(name = "notes")
    private String mNotes;

    @ColumnInfo(name = "start_alert")
    private boolean startAlert;

    @ColumnInfo(name = "end_alert")
    private boolean endAlert;

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long mId) {
        this.assignmentId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getStartDateString() {
        return mStartDateString;
    }

    public void setStartDateString(String mStartDateString) {
        this.mStartDateString = mStartDateString;
    }

    public String getEndDateString() {
        return mEndDateString;
    }

    public void setEndDateString(String mEndDateString) {
        this.mEndDateString = mEndDateString;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public long getCycleId() {
        return mCycleId;
    }

    public void setCycleId(long mCycleId) {
        this.mCycleId = mCycleId;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public boolean hasStartAlert() {
        return startAlert;
    }

    public void setStartAlert(boolean startAlert) {
        this.startAlert = startAlert;
    }

    public boolean hasEndAlert() {
        return endAlert;
    }

    public void setEndAlert(boolean endAlert) {
        this.endAlert = endAlert;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
