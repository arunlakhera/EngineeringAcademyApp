package com.pikchillytechnologies.engineeingacademy.Model;

public class SubCoursePackage {

    private String m_Sub_Course_Id;
    private String m_Sub_Course_Name;
    private String m_Total_Exams;
    private String m_Cost;
    private String m_Payment_Status;
    private String m_User_Id;

    public SubCoursePackage(){ }

    public SubCoursePackage(String sub_course_id, String sub_Course_Name, String total_exams ,String cost, String payment_status, String user_id){

        this.m_Sub_Course_Id = sub_course_id;
        this.m_Sub_Course_Name = sub_Course_Name;
        this.m_Total_Exams = total_exams;
        this.m_Cost = cost;
        this.m_Payment_Status = payment_status;
        this.m_User_Id = user_id;
      }

    public String getM_Sub_Course_Id() {
        return m_Sub_Course_Id;
    }

    public void setM_Sub_Course_Id(String m_Sub_Course_Id) {
        this.m_Sub_Course_Id = m_Sub_Course_Id;
    }

    public String getM_Sub_Course_Name() {
        return m_Sub_Course_Name;
    }

    public void setM_Sub_Course_Name(String m_Sub_Course_Name) {
        this.m_Sub_Course_Name = m_Sub_Course_Name;
    }

    public String getM_Total_Exams() {
        return m_Total_Exams;
    }

    public void setM_Total_Exams(String m_Total_Exams) {
        this.m_Total_Exams = m_Total_Exams;
    }

    public String getM_Cost() {
        return m_Cost;
    }

    public void setM_Cost(String m_Cost) {
        this.m_Cost = m_Cost;
    }

    public String getM_Payment_Status() {
        return m_Payment_Status;
    }

    public void setM_Payment_Status(String m_Payment_Status) {
        this.m_Payment_Status = m_Payment_Status;
    }

    public String getM_User_Id() {
        return m_User_Id;
    }

    public void setM_User_Id(String m_User_Id) {
        this.m_User_Id = m_User_Id;
    }
}
