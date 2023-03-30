package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;

public class Study {
    public String reference;

    @ColumnInfo(name = "study_questions")
    public String studyQuestions;

    public Study() {}

    public Study(String reference, String studyQuestions) {
        this.reference = reference;
        this.studyQuestions = studyQuestions;
    }

    public String getReference() {
        return reference;
    }

    public String getStudyQuestions() {
        return studyQuestions;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setStudyQuestions(String studyQuestions) {
        this.studyQuestions = studyQuestions;
    }
}
