package com.pikchillytechnologies.engineeingacademy.Model;

public class SubCoursePackage {

    private String m_Course_Name;
    private String m_Sub_Course_Name;
    private String m_Cost;
    private String m_Total_Exams;
    private String m_Paid_Flag;

    //public SubCoursePackage(){ }

    public SubCoursePackage(String course_Name, String sub_Course_Name, String cost, String total_Exam, String paid_Flag){

        this.m_Course_Name = course_Name;
        this.m_Sub_Course_Name = sub_Course_Name;
        this.m_Cost = cost;
        this.m_Total_Exams = total_Exam;
        this.m_Paid_Flag = paid_Flag;
    }

    public String getM_Course_Name() {
        return m_Course_Name;
    }

    public void setM_Course_Name(String m_Course_Name) {
        this.m_Course_Name = m_Course_Name;
    }

    public String getM_Sub_Course_Name() {
        return m_Sub_Course_Name;
    }

    public void setM_Sub_Course_Name(String m_Sub_Course_Name) {
        this.m_Sub_Course_Name = m_Sub_Course_Name;
    }

    public String getM_Cost() {
        return m_Cost;
    }

    public void setM_Cost(String m_Cost) {
        this.m_Cost = m_Cost;
    }

    public String getM_Total_Exams() {
        return m_Total_Exams;
    }

    public void setM_Total_Exams(String m_Total_Exams) {
        this.m_Total_Exams = m_Total_Exams;
    }

    public String getM_Paid_Flag() {
        return m_Paid_Flag;
    }

    public void setM_Paid_Flag(String m_Paid_Flag) {
        this.m_Paid_Flag = m_Paid_Flag;
    }
}
