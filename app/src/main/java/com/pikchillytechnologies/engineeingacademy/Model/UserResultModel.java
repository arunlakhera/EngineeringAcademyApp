package com.pikchillytechnologies.engineeingacademy.Model;

public class UserResultModel {

    private String m_User_Id;
    private String m_Exam_Id;
    private String m_Correct;
    private String m_Wrong;
    private String m_Not_Attempted;
    private String m_Total_Marks;
    private String m_Date_Of_Attempt;
    private String m_No_Of_Attempt;

    public UserResultModel(String userid, String examid, String correct, String wrong, String notattempted, String totalmarks, String dateofattempt, String noofattempt){

        this.m_User_Id = userid;
        this.m_Exam_Id = examid;
        this.m_Correct = correct;
        this.m_Wrong = wrong;
        this.m_Not_Attempted = notattempted;
        this.m_Total_Marks = totalmarks;
        this.m_Date_Of_Attempt = dateofattempt;
        this.m_No_Of_Attempt = noofattempt;
    }

    public String getM_User_Id() {
        return m_User_Id;
    }

    public void setM_User_Id(String m_User_Id) {
        this.m_User_Id = m_User_Id;
    }

    public String getM_Exam_Id() {
        return m_Exam_Id;
    }

    public void setM_Exam_Id(String m_Exam_Id) {
        this.m_Exam_Id = m_Exam_Id;
    }

    public String getM_Correct() {
        return m_Correct;
    }

    public void setM_Correct(String m_Correct) {
        this.m_Correct = m_Correct;
    }

    public String getM_Wrong() {
        return m_Wrong;
    }

    public void setM_Wrong(String m_Wrong) {
        this.m_Wrong = m_Wrong;
    }

    public String getM_Not_Attempted() {
        return m_Not_Attempted;
    }

    public void setM_Not_Attempted(String m_Not_Attempted) {
        this.m_Not_Attempted = m_Not_Attempted;
    }

    public String getM_Total_Marks() {
        return m_Total_Marks;
    }

    public void setM_Total_Marks(String m_Total_Marks) {
        this.m_Total_Marks = m_Total_Marks;
    }

    public String getM_Date_Of_Attempt() {
        return m_Date_Of_Attempt;
    }

    public void setM_Date_Of_Attempt(String m_Date_Of_Attempt) {
        this.m_Date_Of_Attempt = m_Date_Of_Attempt;
    }

    public String getM_No_Of_Attempt() {
        return m_No_Of_Attempt;
    }

    public void setM_No_Of_Attempt(String m_No_Of_Attempt) {
        this.m_No_Of_Attempt = m_No_Of_Attempt;
    }
}
