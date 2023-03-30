package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
        tableName = "mentee_assignments",
        primaryKeys = {"cycle_id", "mentee_id", "assignment_id"}
)
public class MenteeAssignmentCrossRef {
    @ColumnInfo(name = "mentee_id")
    private long menteeId;

    @ColumnInfo(name = "assignment_id")
    private long assignmentId;

    @ColumnInfo(name = "cycle_id")
    private long cycleId;

    private String status;

    @ColumnInfo(name = "email_sent_date")
    private String emailSentDate;

    public MenteeAssignmentCrossRef() {}

    public MenteeAssignmentCrossRef(long cycleId, long menteeId, long assignmentId) {
        this.cycleId = cycleId;
        this.menteeId = menteeId;
        this.assignmentId = assignmentId;
        this.status = Status.NOT_EVALUATED.toString();
    }

    public MenteeAssignmentCrossRef(long cycleId, long menteeId, long assignmentId, Status status) {
        this.cycleId = cycleId;
        this.menteeId = menteeId;
        this.assignmentId = assignmentId;
        this.status = status.toString();
    }


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

    public long getCycleId() {
        return cycleId;
    }

    public void setCycleId(long cycleId) {
        this.cycleId = cycleId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public Status getStatusEnum() {
        return Status.fromString(this.status);
    }

    public void setStatusEnum(Status status) {
        this.status = status.toString();
    }

    public String getEmailSentDate() {
        return emailSentDate;
    }

    public void setEmailSentDate(String emailSentDate) {
        this.emailSentDate = emailSentDate;
    }

    public enum Status {
        NOT_EVALUATED("Not Evaluated"),
        NEEDS_WORK("Needs Work"),
        PROFICIENT("Proficient");

        public final String status_value;

        private Status(String status) {
            this.status_value = status;
        }

        public static Status fromString(String status) {
            switch(status) {
                case "Not Evaluated":
                    return Status.NOT_EVALUATED;
                case "Needs Work":
                    return Status.NEEDS_WORK;
                case "Proficient":
                    return Status.PROFICIENT;
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return this.status_value;
        }
    }
}
