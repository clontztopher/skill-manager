package com.example.skillmanager.Data.Entities;

public class EmailReportItem {
    public String mCycleName;
    public String mMenteeName;
    public String mEmailDate;

    public String getCycleName() {
        return mCycleName;
    }

    public void setCycleName(String mCycleName) {
        this.mCycleName = mCycleName;
    }

    public String getMenteeName() {
        return mMenteeName;
    }

    public void setMenteeName(String mMenteeName) {
        this.mMenteeName = mMenteeName;
    }

    public String getEmailDate() {
        return mEmailDate;
    }

    public void setEmailDate(String mEmailDate) {
        this.mEmailDate = mEmailDate;
    }
}
