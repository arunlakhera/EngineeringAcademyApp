package com.pikchillytechnologies.engineeingacademy.Model;

public class ExamListModel {

    private String m_Exam_Id;
    private String m_Exam_Name;
    private String m_Exam_Available_From;
    private String m_Exam_Available_Till;
    private String m_No_Of_Attempts;
    private String m_Exam_Duration;


    public ExamListModel(String exam_Id,String exam_Name, String exam_Available_From, String exam_Available_Till, String no_Of_Attempts, String exam_Duration){

        this.m_Exam_Id = exam_Id;
        this.m_Exam_Name = exam_Name;
        this.m_Exam_Available_From = exam_Available_From;
        this.m_Exam_Available_Till = exam_Available_Till;
        this.m_No_Of_Attempts = no_Of_Attempts;
        this.m_Exam_Duration = exam_Duration;
    }

    public String getM_Exam_Id() {
        return m_Exam_Id;
    }

    public void setM_Exam_Id(String m_Exam_Id) {
        this.m_Exam_Id = m_Exam_Id;
    }

    public String getM_Exam_Name() {
        return m_Exam_Name;
    }

    public void setM_Exam_Name(String m_Exam_Name) {
        this.m_Exam_Name = m_Exam_Name;
    }

    public String getM_Exam_Available_From() {
        return m_Exam_Available_From;
    }

    public void setM_Exam_Available_From(String m_Exam_Available_From) {
        this.m_Exam_Available_From = m_Exam_Available_From;
    }

    public String getM_Exam_Available_Till() {
        return m_Exam_Available_Till;
    }

    public void setM_Exam_Available_Till(String m_Exam_Available_Till) {
        this.m_Exam_Available_Till = m_Exam_Available_Till;
    }

    public String getM_No_Of_Attempts() {
        return m_No_Of_Attempts;
    }

    public void setM_No_Of_Attempts(String m_No_Of_Attempts) {
        this.m_No_Of_Attempts = m_No_Of_Attempts;
    }

    public String getM_Exam_Duration() {
        return m_Exam_Duration;
    }

    public void setM_Exam_Duration(String m_Exam_Duration) {
        this.m_Exam_Duration = m_Exam_Duration;
    }
}
