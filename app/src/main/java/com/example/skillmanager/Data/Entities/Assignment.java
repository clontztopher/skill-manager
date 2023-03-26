package com.example.skillmanager.Data.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignment")
public class Assignment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assignment_id")
    public long assignmentId;

    public String title;

    public String topic;

    public String type;

    @Embedded
    public Project project;

    @Embedded
    public Study study;

    public long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public AssignmentType getType() {
        return AssignmentType.fromString(type);
    }

    public void setType(AssignmentType at) {
        this.type = at.toString();
    }

    public enum AssignmentType {
        PROJECT("Project"),
        STUDY("Study");

        public final String label;
        private AssignmentType(String label) {
            this.label = label;
        }

        public static AssignmentType fromString(String type) {
            switch (type) {
                case "Project":
                    return AssignmentType.PROJECT;
                case "Study":
                    return AssignmentType.STUDY;
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
}
