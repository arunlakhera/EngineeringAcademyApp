package com.pikchillytechnologies.engineeingacademy.Model;

public class CoursesModel {

    private String m_Category_Id;
    private String m_Name;
    private String m_Status;

    public CoursesModel(){ }

    public CoursesModel(String category_id, String name, String status){

        this.m_Category_Id = category_id;
        this.m_Name = name;
        this.m_Status = status;
    }

    public String getM_Category_Id() {
        return m_Category_Id;
    }

    public void setM_Category_Id(String m_Category_Id) {
        this.m_Category_Id = m_Category_Id;
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String m_Name) {
        this.m_Name = m_Name;
    }

    public String getM_Status() {
        return m_Status;
    }

    public void setM_Status(String m_Status) {
        this.m_Status = m_Status;
    }
}
