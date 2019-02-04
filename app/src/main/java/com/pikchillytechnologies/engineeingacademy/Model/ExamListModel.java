package com.pikchillytechnologies.engineeingacademy.Model;

public class ExamListModel {

    private String m_Exam_Name;
    private String m_Exam_Available_Date;
    private String m_Exam_Till_Date;

    public ExamListModel(String examName, String examAvailableDate, String examTillDate){

        this.m_Exam_Name = examName;
        this.m_Exam_Available_Date = examAvailableDate;
        this.m_Exam_Till_Date = examTillDate;

    }

    public String getM_Exam_Name() {
        return m_Exam_Name;
    }

    public void setM_Exam_Name(String m_Exam_Name) {
        this.m_Exam_Name = m_Exam_Name;
    }

    public String getM_Exam_Available_Date() {
        return m_Exam_Available_Date;
    }

    public void setM_Exam_Available_Date(String m_Exam_Available_Date) {
        this.m_Exam_Available_Date = m_Exam_Available_Date;
    }

    public String getM_Exam_Till_Date() {
        return m_Exam_Till_Date;
    }

    public void setM_Exam_Till_Date(String m_Exam_Till_Date) {
        this.m_Exam_Till_Date = m_Exam_Till_Date;
    }
}
